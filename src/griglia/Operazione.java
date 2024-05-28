package griglia;

import griglia.interfacce.Operable;

import java.util.List;

public enum Operazione implements Operable {
    Addizione{
        public int doOp(List<Integer>l){
            int res = 0;
            for(Integer el:l)
                res+=el;
            return res;
        }
    },
    Sottrazione{
        public int doOp(List<Integer>l){
            int res = 0;
            boolean primo = true;
            for(Integer el:l){
                if(primo) {
                    res = el;
                    primo = false;
                }
                else
                    res -= el;
            }
            return res;
        }

    },
    Moltiplicazione{
        public int doOp(List<Integer>l){
            int res = 1;
            for(Integer el:l){
                res *= el;
            }
            return res;
        }

    },
    Divisione{
        public int doOp(List<Integer>l){
            int res = 0;
            boolean primo = true;
            for(Integer el:l){
                if(primo){
                    res = el;
                    primo = false;
                }
                else
                    res = res / el;
            }
            return res;
        }

    };

    //public static Operaation getRandomOp(List<Operation> notIcluded
    //private sttaic List<Operation> avaibleOperations(List<Operation> notIncluded


}

