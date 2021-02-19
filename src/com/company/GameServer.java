package com.company;

public class GameServer
{
    private static GameServer instance;

    public static void main(String[] args)
    {
        GameServer server = new GameServer("134.123.31.24", 2);

        server.entities    = new Entity[3];
        server.entities[0] = new EntityPlayer("player", 21, -10,                  40, 10);
        server.entities[1] =       new Entity("enemy1", 10 , 10  , true , 46, 10);
        server.entities[2] =       new Entity("enemy2", 10, 4 , true , 11, 5);

        long delay = 1000 / 1;

        while (true)
        {
            try {
                Thread.sleep(delay);
                server.updateServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static GameServer getInstance()
    {
        return instance;
    }

    private String ip;
    private int difficulty;
    private Entity[] entities;

    public GameServer(String ip, int difficulty)
    {
        this.ip = ip;
        this.difficulty = difficulty;
        instance = this;
    }

    public void updateServer()
    {
        System.out.println("========== Server update ==========");
        for (Entity entity: entities)
        {
            if (entity != null) entity.update();
        }
    }

    public Entity[] getEntities() { return entities; }
    public int getDifficulty() { return difficulty; }
}
