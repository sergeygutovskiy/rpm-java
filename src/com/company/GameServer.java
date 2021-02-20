package com.company;

public class GameServer
{
    private static GameServer instance;

    public static void main(String[] args)
    {
        // init game server with ip and difficulty
        GameServer server = new GameServer("134.123.31.24", 2);

        // create player and enemies
        server.entities    = new Entity[3];
        server.entities[0] = new EntityPlayer("player", 21, -10, 40, 10);
        server.entities[1] =       new Entity("enemy1", 10 , 10, true , 46, 10);
        server.entities[2] =       new Entity("enemy2", 10, 4, true , 11, 5);

        // test toString override
        System.out.println("\033[0;34m" + "========== Server info ==========" + "\033[0m");
        System.out.println(server);
        for (Entity e : server.entities)
            System.out.println(e);

        // calculate sleep time for 1 FPS
        long delay = 1000 / 1;

        // run game cycle
        while (server.run)
        {
            try 
            {
                Thread.sleep(delay);
                server.updateServer();
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }

        System.out.println("\033[0;34m" + "========== Server closed ==========" + "\033[0m");
        System.out.println(server);
    }

    public static GameServer getInstance()
    {
        return instance;
    }

    private String ip;
    private int difficulty;
    private Entity[] entities;
    private int actionsOnUpdateCount;
    private boolean run;

    public GameServer(String ip, int difficulty)
    {
        this.ip = ip;
        this.difficulty = difficulty;
        this.actionsOnUpdateCount = 0;
        this.run = true;

        this.instance = this;
    }

    public void updateServer()
    {
        System.out.println("\033[0;34m" + "========== Server update ==========" + "\033[0m");
        
        // reset action on update
        actionsOnUpdateCount = 0;

        // run update event for all entities 
        for (Entity entity: entities)
        {
            // run update only if entity still alive (not null)
            if (entity != null) entity.update();
        }

        // if there is no happens than end game
        if (actionsOnUpdateCount == 0) run = false;
    }

    public Entity[] getEntities() { return entities; }
    public void setEntities(Entity[] entities) { this.entities = entities; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public String getip() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public int getActionsOnUpdateCount() { return actionsOnUpdateCount; }
    public void setActionsOnUpdateCount(int count) { actionsOnUpdateCount = count; }

    public void increaseActions() { actionsOnUpdateCount++; }

    public boolean getRun() { return run; }
    public void setRun(boolean run) { this.run = run; }

    @Override
    public String toString() 
    { 
        return new String("[game server] ip: " + ip + " difficulty: " + difficulty); 
    } 
}
