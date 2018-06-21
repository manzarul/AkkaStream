package akka_stream.akka_stream;

import java.util.concurrent.CompletionStage;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
/**
 * Simple Akka steam example.
 * @author Manzarul
 *
 */
public class App 
{
	
	public static volatile int primeCount = 0 ;
    public static void main( String[] args )
    {
       // Create a actor system here 
    	final ActorSystem system = ActorSystem.create("QuickStart");
    	//With the help of actor system create Materializer , this is factor of Actor stream
        final Materializer materializer = ActorMaterializer.create(system);
        // generate number between start to end and having difference of 5.
        // it will just generate the Source object
        final Source<Integer, NotUsed> source = Source.range(0, 1000000000);
        // now doing the command to source object run as stream and used Akka Materializer obj 
        // this will run in Async once task is completed it will return us CompletionStage
        
        final CompletionStage<Done> done = source.runForeach(i -> { if (isPrime(i)) { System.out.println("primer==" + i);}
        }, materializer);
        done.thenRun(() ->{ system.terminate(); System.out.println("Prime Count...."+ primeCount);});
        System.out.println("Completed....");
    }
    
    
    private static boolean isPrime(int i) {
    	if(i==0 || i ==1) 
    		return false;
       int sqrt = (int)Math.sqrt(i);
       for (int num =2 ; num<=sqrt ; num++) {
    	   if (i%num ==0) 
    		   return false;
       }
       primeCount ++;
       return true;
    	
    	
    }
}
