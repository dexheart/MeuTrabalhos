package ppcWork;
import java.util.LinkedList; 


public class PPCtrab 
{ 
	
    public static void main(String[] args) 
                        throws InterruptedException 
    { 
        	
    	final control pc = new control(); 
    	
        Thread coz = new Thread(new Runnable() 
        { 
            @Override
            public void run() 
            { 
                try
                { 
                    pc.cozinheiro("Cozinheiro"); 
                } 
                catch(InterruptedException e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
        
        Thread c1 = new Thread(new Runnable() 
        { 
            @Override
            public void run() 
            { 
                try
                { 
                    pc.canibal("A"); 
                } 
                catch(InterruptedException e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
        });
        
        Thread c2 = new Thread(new Runnable() 
        { 
            @Override
            public void run() 
            { 
                try
                { 
                    pc.canibal("B"); 
                } 
                catch(InterruptedException e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
        });
        
        Thread c3 = new Thread(new Runnable() 
        { 
            @Override
            public void run() 
            { 
                try
                { 
                    pc.canibal("C"); 
                } 
                catch(InterruptedException e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
        });
  
        c1.start(); 
        c2.start();
        c3.start();
        coz.start(); 

        
    } 
  

    public static class control 
    { 

        LinkedList<Integer> list = new LinkedList<>(); 
        int capacidade = 5; 
        
        int contA, contB, contC = 0 ;
        
        public void cozinheiro(String X) throws InterruptedException 
        { 
            int value = 0; 
            
        	long start = System.currentTimeMillis();

            
            while (true) 
            { 
                synchronized (this) 
                { 
                	
                	//Se o caldeirão não tiver vazio, o cozinheiro dorme
                    while (list.size()!=0) {
                		System.out.println("Caldeirão não está vazio, cozinheiro vai dormir");

//                    	if(list.size()==0) {
//                    		System.out.println("Caldeirão não está vazio, cozinheiro vai dormir");
//                    	}
                    	wait();
                    }
             
                    //System.out.println();          
                    //System.out.println();
                    
                    prepararJantar(list, value, contA, contB, contC, start);
                 
                    //System.out.println();
  
                    if(list.size()==capacidade) {
                        notifyAll();
                        System.out.println("Caldeirão cheio, acordar todos os canibais");

                    }

                } 
            } 
        } 
  
        // Function called by consumer thread 
        public void canibal(String Y) throws InterruptedException 

        { 
        	
        	
            while (true) 
            { 
                synchronized (this) 
                { 

                    while (list.size()==0) {
                    	System.out.println("Caldeirão vazio, O Canibal " + Y + " irá dormir");
                        wait();
                    }

                    servir(Y, list);
                    notifyAll();
                } 
                
                comer(Y, list);

                if(Y=="A") {
            		if(contA>contB && contA>contC) {
            			Thread.sleep(8000);
            		}
            	}else if(Y=="B") {
            		if(contB>contA && contB>contC) {
            			Thread.sleep(8000);
            		}
            	}else if(Y=="C") {
            		if(contC>contA && contC>contB) {
            			Thread.sleep(8000);
            		}
            	}
                
            } 
        }
        
        
        public void servir(String nome, LinkedList<Integer> X) throws InterruptedException {
        	System.out.println("Canibal "+ nome + " está se servindo");
        	//System.out.println("... Em um segundo ...");
        	Thread.sleep(1000);
        	X.removeFirst();
        	System.out.println("Status do caldeirão: " + X.size() + "/5");
        	//System.out.println("Canibal "+ nome + " terminou de se servir");
        }
        
        public void comer(String nome, LinkedList<Integer> X) throws InterruptedException {
        	System.out.println("Canibal "+ nome + " está comendo");
        	//System.out.println("... Em três segundos ...");
        	//Thread.sleep(3000);
        	
        	
        	if(nome=="A") {
            	contA++;
            	Thread.sleep(3000);

            	//System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
            else if(nome=="B") {
            	contB++;
            	Thread.sleep(3000);

            	//System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
            else if(nome=="C") {
            	contC++;
            	Thread.sleep(3000);

            	//System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
        	//System.out.println("Canibal "+ nome + " terminou de comer");
            //System.out.println("Status do caldeirão: " + X.size() + "/5");

        }
        
        public void prepararJantar(LinkedList<Integer> X, int value, int A, int B, int C, Long start) throws InterruptedException {
        	float temp;
        	
        	
        	System.out.println("Cozinheiro Preparando a janta");
        	//System.out.println("... Em cinco segundos ...");
        	Thread.sleep(5000);
        	for(int i= 0;i<5;i++) {
                    X.add(value++); 
               }
        	
        	temp = calcTempo(start);
        	System.out.print("Cozinheiro terminou de preparar a janta \n" + "Status do caldeirão: " + X.size() + "/5" +  "\n" + " O Canibal A, comeu " + A +" vezes.\n" + " O Canibal B, comeu " + B +" vezes.\n" + " O Canibal C, comeu " + C +" vezes.\n" + "Ocorreram exatamente " + temp + " minutos.\n");
        	
    		if(temp > 2) {
    			System.out.println("||========================================================||");
    			System.out.println("     Já se passaram 2 minutos, o Programa será fechado");
    			System.out.println("||========================================================||");
    			System.exit(0);
    		}
        	
        	
        }
        
        public float calcTempo(Long inicial) {
        	
        	long elapsedTimeMillis;
        	float elapsedTimeMin;
        	
    		elapsedTimeMillis = System.currentTimeMillis()-inicial;

    		elapsedTimeMin = elapsedTimeMillis/(60*1000F);
    		
		
    		return elapsedTimeMin; 
    					
        }
              
    }
    
    

    
} 