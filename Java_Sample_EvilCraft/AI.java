//AI vs AI game engine.  Load 2 AI engines and have them attack eachother and make sure they cannot change the sprite status and brute force a win. 
package EvilCraft;

import java.util.ArrayList;
/**
 *
 * @author eddenk
 */
public class AI {
    //-- data members 
    protected Team team;
    protected ButtonController btnController;
    
    //-- operations
    public AI(Team team, ButtonController btnController){
        this.team = team;
        this.btnController = btnController;
    }
    
    /**
     * Called by GameEngine.onTick() for every 150 ticks.
     * Make decision based on enemy info
     */
    
    protected int ticks = -1;
    public void update(){
        ticks++;
        //2. make the units
        if(ticks%50==0){
            this.btnController.spawnInfantry();
        }
        if(ticks%50==25){
            this.btnController.spawnTank();
        }
        if(ticks%50==49){
            this.btnController.spawnAircraft();
        }
        
        if(ticks%150!=0){
            return;
        }
        //1. get the data
        GameEngine ge = GameEngine.getInstance();
        TeamInfo enemyTeam = ge.getEnemyTeamInfo(this.team);
        
               
        //3. make half attack goals
        int nxtTarget = 0;
        ArrayList<SpriteInfo> arrEnemy = enemyTeam.getSpritesInfo();
        int half = this.team.getSprites().size()/2;
        for(int i=0; i<half; i++){
            Sprite sp = this.team.getSprites().get(i);
            SpriteInfo target = arrEnemy.get(nxtTarget);
            sp.setNavigationGoal(new Point(target.x, target.y));
            sp.setAttackGoal(target);
            nxtTarget = (nxtTarget+1)%arrEnemy.size();
        }
        
        //4. make rest attack base
        SpriteInfo ebase = enemyTeam.getBaseInfo();
        for(int i=half; i<this.team.getSprites().size(); i++){
            Sprite sp = this.team.getSprites().get(i);
            sp.setNavigationGoal(new Point(ebase.x, ebase.y));
            sp.setAttackGoal(ebase);
        }
    }
    
}