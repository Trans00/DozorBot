package com.dmgburg.dozor

interface KsRepository {
    public void setKs(List<String> ks)
    public Map<Integer,String> getKs()
    public void removeKs(int number)
    }