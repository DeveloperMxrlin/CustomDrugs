package mxrlin.customdrugs.helper.mysql;

import mxrlin.customdrugs.CustomDrug;
import mxrlin.customdrugs.drugs.Drug;
import mxrlin.customdrugs.drugs.DrugHandler;
import org.bukkit.potion.PotionEffectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrugMySQL {

    private static DrugHandler handler = CustomDrug.instance.getHandler();

    public static void importDrugs(){

        List<String> drugnames = new ArrayList<>();
        ResultSet rs = MySQL.getResult("SELECT * FROM drugs");

        try{
            while(rs.next()){
                drugnames.add(rs.getString("Name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        for(String drugname : drugnames){

            String description = (String) getInfoFromDrug(drugname, "Description");
            String finaldesc = description.replaceAll("_", " ");
            int duration = (int) getInfoFromDrug(drugname, "Duration");
            int sellprice = (int) getInfoFromDrug(drugname, "SellPrice");
            int buyprice = (int) getInfoFromDrug(drugname, "BuyPrice");
            List<PotionEffectType> effectTypes = new ArrayList<>();

            String effectstring1 = (String) getInfoFromDrug(drugname, "Effect1");
            if(!effectstring1.equals("NULL")) effectTypes.add(PotionEffectType.getByName(effectstring1));

            String effectstring2 = (String) getInfoFromDrug(drugname, "Effect2");
            if(!effectstring2.equals("NULL")) effectTypes.add(PotionEffectType.getByName(effectstring2));

            String effectstring3 = (String) getInfoFromDrug(drugname, "Effect3");
            if(!effectstring3.equals("NULL")) effectTypes.add(PotionEffectType.getByName(effectstring3));

            Drug d = new Drug(drugname, description, effectTypes, duration, sellprice, buyprice);
            handler.getDrugObjects().put(drugname, d);

        }

    }

    private static Object getInfoFromDrug(String drugname, String info){

        ResultSet rs = MySQL.getResult("SELECT * FROM drugs WHERE Name='" + drugname + "'");

        try{
            if(rs.next()){
                return rs.getObject(info);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        return "";

    }

    public static void saveDrugs(){

        Map<String, Drug> drugs = handler.getDrugObjects();



        if(drugs.size() > 0){

            ResultSet rs = MySQL.getResult("SELECT * FROM drugs");
            List<String> names = new ArrayList<>();

            try{
                while (rs.next()){

                    names.add(rs.getString("Name"));

                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try{
                    rs.close();
                }catch (SQLException e2){
                    e2.printStackTrace();
                }
            }

            for(Drug drug : drugs.values()){

                if(names.contains(drug.getName())) return;

                final List<String> effectList = new ArrayList<>();

                for(PotionEffectType type : drug.getDrugEffectTypes()){
                    effectList.add(type.getName());
                }

                if(effectList.size() > 3) return;

                String effect1 = (effectList.get(0) == null ? "NULL" : effectList.get(0));
                String effect2 = (effectList.get(1) == null ? "NULL" : effectList.get(1));
                String effect3 = (effectList.get(2) == null ? "NULL" : effectList.get(2));

                MySQL.update("INSERT INTO drugs (Name, Description, Effect1, Effect2, Effect3, Duration, SellPrice, BuyPrice) VALUES ('"
                        + drug.getName() + "','"
                        + drug.getDescription().replaceAll(" ", "_") + "','"
                        + effect1 + "','"
                        + effect2 + "','"
                        + effect3 + "','"
                        + drug.getDrugDurationInSeconds() + "','"
                        + drug.getSellPrice() +"','"
                        + drug.getBuyPrice() + "')");

            }

        }

    }

}
