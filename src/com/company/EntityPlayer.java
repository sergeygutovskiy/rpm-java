package com.company;

public class EntityPlayer extends Entity {
    private String nickname;

    public EntityPlayer(String title, int posX, int posZ, int maxHealth, int attackDamage)
    {
        super(title, posX, posZ, false, maxHealth, attackDamage);
        this.nickname = title;
    }

    public void update()
    {

    }

    public String getNickname() { return nickname; }
    public void setNickname( String nickname) { this.nickname = nickname; }
}
