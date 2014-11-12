package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
    	
        System.out.println("Starting Cache Client...");
        ArrayList<CacheServiceInterface> serverList = new ArrayList<CacheServiceInterface>();
        CacheServiceInterface server1 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface server2 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface server3 = new DistributedCacheService("http://localhost:3002");
        CacheServiceInterface temp;
       
        serverList.add(server1);
        serverList.add(server2);
        serverList.add(server3);
        ConsistentHash<CacheServiceInterface> consistentHash = new ConsistentHash(Hashing.md5(), serverList);
               
        for (CacheServiceInterface cachesi : serverList)
        	 consistentHash.add(cachesi);
        
        String mapValue;
        int i = 1;
        String str[] = {"x", "a","b","c","d","e","f","g","h","i","j"};
        for(i=1;i<11;i++){
        	temp = consistentHash.get(i);
        	temp.put(i,str[i]);
        	mapValue = temp.get(i);
        	System.out.println("get("+i+") => " + mapValue);
        }
        
        int serverCount = serverList.size(); 
        
        System.out.println("Finishing Cache Client"+serverCount);
    }
    
}
