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

    public Entity(
        String title, 
        int posX, 
        int posZ, 
        boolean aggressive, 
        int maxHealth, 
        int attackDamage
    )
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
        // finds enemies if only self is aggresive 
        if (aggressive)
        {
            // finds nearest frienldy entity
            Entity entity = findNearestNotAggressiveEntity();
            // checks for entity life state (not null)
            if (entity != null)
            {
                // find dist for target entity 
                double dist = Math.abs(
                    Math.sqrt(entity.posX * entity.posX + entity.posZ * entity.posZ)
                    - Math.sqrt(posX * posX + posZ * posZ)
                );

                // if dist less then const: 
                if (dist < 2.0) attack(entity); // attack
                // else
                else moveTo(entity); // move in direction to target 
            }
        }
    }

    public void attack(Entity entity)
    {
        GameServer.getInstance().increaseActions();

        int damage = attackDamage;
        // if entity is player then increase attack with difficulty value 
        if (this instanceof EntityPlayer)
            damage += (int)(0.5 * GameServer.getInstance().getDifficulty());

        System.out.print(title + " attacked " + entity.title + " with damage=" + damage);

        // attack entity 
        entity.setHealth(entity.getHealth() - damage);

        System.out.print(" [" + entity.title + "'s health: " + entity.health + "]");
        System.out.println("");

        // if target entity died
        if (entity.getHealth() <= 0)
        {
            System.out.println(
                "\033[0;31m" + entity.title + " died by " + title + "\033[0m"
            );
            
            // run throw all game's entities and "delete" one that died (set to null)
            for (int i = 0; i < GameServer.getInstance().getEntities().length; i++)
            {
                if (GameServer.getInstance().getEntities()[i].id == entity.id)
                {
                    GameServer.getInstance().getEntities()[i] = null;
                    return;
                }
            }
        }
        // else if entity is player, then player attacks back 
        else if (entity instanceof EntityPlayer)
        {
            entity.attack(this);
        }
    }

    public void moveTo(Entity entity)
    {
        GameServer.getInstance().increaseActions();

        System.out.print(this.title + " moved from: " + this.posX + ", " + this.posZ);

        // target for right side of entity -> go right 
        if (posX < entity.posX) posX++;
        // target for left side of entity -> go left
        else if (posX > entity.posX) posX--;

        // same logic
        if (posZ < entity.posZ) posZ++;
        else if (posZ > entity.posZ) posZ--;

        System.out.print(" to: " + this.posX + ", " + this.posZ);
        System.out.print(" [target: " + entity.title + " (" + entity.posX + ", " + entity.posZ + ")]");
        System.out.println("");
    }

    public Entity findNearestNotAggressiveEntity()
    {
        // get all entities
        Entity[] entities = GameServer.getInstance().getEntities();
        // min dist to enemy
        double minDis = 999;
        // index of target enemy
        int minDisIndex = -1;

        for (int i = 0; i < entities.length; i++)
        {
            // entity alive
            if (entities[i] != null)
            {
                // calculate dist
                double dis = Math.abs(
                    Math.sqrt(
                        entities[i].posX * entities[i].posX 
                        + entities[i].posZ * entities[i].posZ
                    ) 
                    - Math.sqrt(posX * posX + posZ * posZ)
                );
                // checks for new nearest enemy 
                if (dis < minDis && dis < 20 && !entities[i].getAggressive()) 
                {
                    minDis = dis;
                    minDisIndex = i;
                }
            }
        }

        // if no target found then return nothing
        if (minDisIndex == -1) return null;
        // else return target
        else return entities[minDisIndex];
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

    @Override
    public String toString() 
    { 
        return new String(
            "[entity] title: " + title + " health: " + health
        ); 
    } 
}
