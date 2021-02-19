package com.company;

public class Entity {
    private static int idCounter = 1;
    final long id = idCounter;

    protected String title;

    protected int posX;
    protected int posZ;

    protected boolean aggressive;
    protected int maxHealth;
    protected int health;
    protected int attackDamage;

    public Entity(String title, int posX, int posZ, boolean aggressive, int maxHealth, int attackDamage)
    {
        this.title = title;
        this.posX  = posX;
        this.posZ  = posZ;
        this.aggressive   = aggressive;
        this.maxHealth    = maxHealth;
        this.health       = maxHealth;
        this.attackDamage = attackDamage;

        idCounter++;
    }

    public void update()
    {
        if (aggressive)
        {
            Entity entity = findNearestNotAggressiveEntity();
            if (entity != null)
            {
                double dist = Math.abs(
                        Math.sqrt(entity.posX * entity.posX + entity.posZ * entity.posZ)
                                - Math.sqrt(posX * posX + posZ * posZ)
                );

                if (dist < 2.0) {
                    attack(entity);
                } else {
                    moveTo(entity);
                }
            }
        }
    }

    public void attack(Entity entity)
    {
        int damage = attackDamage;
        if (this instanceof EntityPlayer)
        {
            damage += (int)(0.5 * GameServer.getInstance().getDifficulty());
            System.out.println(damage + " " + GameServer.getInstance().getDifficulty());
        }

        System.out.print(title + " attacked " + entity.title + " with damage=" + damage);
        entity.setHealth(entity.getHealth() - damage);
        System.out.print(" [" + entity.title + "'s health: " + entity.health + "]");
        System.out.println("");

        if (entity.getHealth() <= 0)
        {
            System.out.println(entity.title + " died by " + title);
            for (int i = 0; i < GameServer.getInstance().getEntities().length; i++)
            {
                if (GameServer.getInstance().getEntities()[i].id == entity.id)
                {
                    GameServer.getInstance().getEntities()[i] = null;
                    return;
                }
            }
            return;
        }
        else if (entity instanceof EntityPlayer)
        {
            entity.attack(this);
        }
    }

    public void moveTo(Entity entity)
    {
        System.out.print(this.title + " moved from: " + this.posX + ", " + this.posZ);

        if (posX < entity.posX) posX++;
        else if (posX > entity.posX) posX--;

        if (posZ < entity.posZ) posZ++;
        else if (posZ > entity.posZ) posZ--;

        System.out.print(" to: " + this.posX + ", " + this.posZ);
        System.out.print(" [target: " + entity.title + " (" + entity.posX + ", " + entity.posZ + ")]");
        System.out.println("");
    }

    public Entity findNearestNotAggressiveEntity()
    {
        Entity[] entities = GameServer.getInstance().getEntities();
        double minDis = 999;
        int minDisIndex = -1;

        for (int i = 0; i < entities.length; i++)
        {
            if (entities[i] != null)
            {
                double dis = Math.abs(
                        Math.sqrt(entities[i].posX * entities[i].posX + entities[i].posZ * entities[i].posZ)
                                - Math.sqrt(posX * posX + posZ * posZ)
                );
                if (dis < minDis && !entities[i].getAggressive()) {
                    minDis = dis;
                    minDisIndex = i;
                }
            }
        }

        if (minDisIndex == -1)
            return null;
        else
            return entities[minDisIndex];
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosZ() { return posZ; }
    public void setPosZ(int posZ) { this.posZ = posZ; }

    public boolean getAggressive() { return aggressive; }
    public void setAggressive(boolean aggressive) { this.aggressive = aggressive; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getAttackDamage() { return attackDamage; }
    public void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }
}
