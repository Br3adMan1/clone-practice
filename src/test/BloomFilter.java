package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private BitSet bits;
    private  String[] algs;


    public BloomFilter(int bitsSize, String... algs) {
        this.bits = new BitSet(bitsSize);
        this.algs = algs.clone();
    }

    public void add(String word) {
        for(String s : algs) {
            try {
                MessageDigest md=MessageDigest.getInstance(s);
                byte[] bts=md.digest(word.getBytes());
                BigInteger big= new BigInteger(bts);
                int index = Math.abs(big.intValue());
                index = index % bits.size();
                bits.set(index);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean contains(String word){
        for(String s : algs) {
            try {
                MessageDigest md=MessageDigest.getInstance(s);
                byte[] bts=md.digest(word.getBytes());
                BigInteger big= new BigInteger(bts);
                int index = Math.abs(big.intValue());
                index = index % bits.size();
                if(!bits.get(index))
                    return false;

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            sb.append(bits.get(i) ? "1" : "0");
        }
        return sb.toString();
    }



}
