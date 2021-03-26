package mxrlin.customdrugs.helper;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.entity.Player;

public class DrugEconomy {

    private Player p;
    private double amount;

    public DrugEconomy(Player p, double amount) {
        this.p = p;
        this.amount = amount;
    }

    public double getMoney() {
        try {
            return Economy.getMoney(p.getName());
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        return 0.0D;
    }

    public void setMoney(){
        try {
            Economy.setMoney(p.getName(), amount);
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(){
        try {
            Economy.add(p.getName(), amount);
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
        }
    }

    public void remMoney(){
        try {
            Economy.subtract(p.getName(), amount);
        } catch (UserDoesNotExistException | NoLoanPermittedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasEnoughMoney(){
        try {
            return Economy.hasEnough(p.getName(), amount);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
