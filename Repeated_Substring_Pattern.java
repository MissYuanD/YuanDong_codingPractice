/*
Leetcode 459 : https://leetcode.com/problems/repeated-substring-pattern/description/
Given a non-empty string check if it can be constructed by taking a substring of it and appending 
multiple copies of the substring together. You may assume the given string consists of lowercase 
English letters only and its length will not exceed 10000.

Example 1:
Input: "abab"

Output: True

Explanation: It's the substring "ab" twice.
Example 2:
Input: "aba"

Output: False
Example 3:
Input: "abcabcabcabc"

Output: True

Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
*/
public class Solution {
    // solution 1
    /*
    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() == 0) return false;
        String c = (s + s).substring(1, s.length() + s.length() - 1);
        return c.indexOf(s) != -1;
    }
    */
    
    // solution 2 : kmp
     public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() == 0) return false;
        int[] table = getKMPTable(s);
        int n = s.length();
        return table[n] > 0 && table[n] % (n - table[n]) == 0;
    }
    
    private int[] getKMPTable(String input) {
        int n = input.length();
        int[] table = new int[n + 1];
        table[0] = table[1] = 0;
        for (int i = 1; i < n; i++) {
            int p = table[i];
            while(p > 0 && input.charAt(p) != input.charAt(i)) {
                p = table[p];
            }
            table[i + 1] = input.charAt(p) != input.charAt(i) ? 0 : p + 1;
        }
        return table;
    }
}