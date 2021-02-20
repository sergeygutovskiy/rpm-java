package com.company;

public class EntityPlayer extends Entity {
    private String nickname;
    private int updatesFromLastRegenerating;

    public EntityPlayer(String title, int posX, int posZ, int maxHealth, int attackDamage)
    {
        super(title, posX, posZ, false, maxHealth, attackDamage);
        
        this.nickname = title;
        this.updatesFromLastRegenerating = 0;
    }

    @Override
    public void update()
    {
        super.update();

        updatesFromLastRegenerating++;
        if (updatesFromLastRegenerating == 2)
        {
            updatesFromLastRegenerating = 0;
            if (health < maxHealth)
            {
                health++;
                System.out.println(
                    "\033[0;32m" + nickname + " regenerate 1HP, now is " + health + "HP" 
                    + "\033[0m"
                );
            }
        }
    }

    public String getNickname() { return nickname; }
    public void setNickname( String nickname) { this.nickname = nickname; }

    public int getUpdatesFromLastRegenerating() { return updatesFromLastRegenerating; }
    public void setUpdatesFromLastRegenerating( int updates)
    { 
        updatesFromLastRegenerating = updates; 
    }    

    @Override
    public String toString() 
    { 
        return new String(
            "[player] nickname: " + nickname + " health: " + health
        ); 
    } 
}
