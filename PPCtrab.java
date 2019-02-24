import java.util.LinkedList; 

public class PPCtrab 
{ 
    public static void main(String[] args) 
                        throws InterruptedException 
    { 
        // Object of a class that has both produce() 
        // and consume() methods 
        
    	final PC pc = new PC(); 
    	

  
        // Create producer thread 
        Thread t1 = new Thread(new Runnable() 
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
  
        // Create consumer thread 
        Thread t2 = new Thread(new Runnable() 
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
        
        Thread t3 = new Thread(new Runnable() 
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
        
        Thread t4 = new Thread(new Runnable() 
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
  
        // Start both threads 
        t1.start(); 
        t2.start(); 
        t3.start();
        t4.start();
  
        // t1 finishes before t2 
        //t1.join(); 
        //t2.join(); 
        //t3.join();
        //t4.join();
    } 
  
    // This class has a list, producer (adds items to list 
    // and consumber (removes items). 
    public static class PC 
    { 
        // Create a list shared by producer and consumer 
        // Size of list is 2. 
        LinkedList<Integer> list = new LinkedList<>(); 
        int capacidade = 5; 
        
        int contA, contB, contC = 0 ;
        
  
        // Function called by producer thread 
        public void cozinheiro(String X) throws InterruptedException 
        { 
            int value = 0; 
            while (true) 
            { 
                synchronized (this) 
                { 
                	
                	//Se o caldeirão não tiver vazio, o cozinheiro dorme
                    while (list.size()>0) {
                    	if(list.size()==0) {
                    		System.out.println("Caldeirão não está vazio, cozinheiro vai dormir");
                    	}
                    	wait();
                    }
                         
                    
                    //System.out.println();

                    
                	

                    
                    //System.out.println();
                    
                    prepararJantar(list, value, contA, contB, contC);
                 
                    //System.out.println();
  
                    if(list.size()==capacidade) {
                        notifyAll();
                        System.out.println();
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
                        System.out.println("O Canibal " + Y + " acordou");
                        //notifyAll();

                    }

                    servir(Y, list);                      
                    notifyAll();
                } 
                
                
                comer(Y, list);
                
//                synchronized (this) 
//                { 
//                	
//  	
//                    while (list.size()==0) {
//                    	System.out.println("Caldeirão vazio, O Canibal " + Y + " irá dormir");
//                    	
//                        wait();
//                        System.out.println("O Canibal " + Y + " acordou");
//                        //notifyAll();
//
//                    }
//                     
//                    notifyAll();
//                } 
                
        
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
        	Thread.sleep(3000);
        	
        	
        	if(nome=="A") {
            	contA++;
            	System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
            else if(nome=="B") {
            	contB++;
            	System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
            else if(nome=="C") {
            	contC++;
            	System.out.print("O Canibal " + nome + " terminou comer.\n");
            }
        	//System.out.println("Canibal "+ nome + " terminou de comer");
            //System.out.println("Status do caldeirão: " + X.size() + "/5");

        }
        
        public void prepararJantar(LinkedList<Integer> X, int value, int A, int B, int C) throws InterruptedException {
        	System.out.println("Cozinheiro Preparando a janta");
        	//System.out.println("... Em cinco segundos ...");
        	Thread.sleep(5000);
        	for(int i= 0;i<5;i++) {
                    X.add(value++); 
               }
        	System.out.print("Cozinheiro terminou de preparar a janta \n" + "Status do caldeirão: " + X.size() + "/5" +  "\n" + " O Canibal A, comeu " + A +" vezes.\n" + " O Canibal B, comeu " + B +" vezes.\n" + " O Canibal C, comeu " + C +" vezes.\n");
        	
        }
        
        
       
        
    }
    
    

    
} 