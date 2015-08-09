package com.dmgburg.dozor

import java.util.concurrent.ConcurrentHashMap

@Singleton
class KsRepositoryImpl implements KsRepository {
    private Map<Integer,String> ksByNumber =  new ConcurrentHashMap<>()

    public void setKs(List<String> ks){
        ksByNumber.clear()
        int i = 1
        ks.each {String it ->
            ksByNumber.put(i++,it)
        }
    }

    public Map<String,String> getKs(){
        return ksByNumber
    }

    public void removeKs(int number){
        ksByNumber.remove(number)
    }
}
