package com.dmgburg.dozor

interface KsRepository {
    public void setKs(List<String> ks)
    public Map<String,String> getKs()
    public void removeKs(int number)
    public String tryCode(String code)
    }