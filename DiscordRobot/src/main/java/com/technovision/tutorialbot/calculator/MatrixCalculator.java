package com.technovision.tutorialbot.calculator;


public class MatrixCalculator {
    public Double[][] matrix1 = new Double[1][1];
    public Double[][] matrix2 = new Double[1][1];
    public Double[][] matrix3 = new Double[1][1];
    private Double[][] storeMatrix = new Double[1][1];
    private int rankOfMatrix1;
    private int rankOfMatrix2;

    public boolean isThereAMatrix1 = false;
    public boolean isThereAMatrix2 = false;
    public boolean isThereAMatrix3 = false;

    public void setMatrix1(int row, int column) {
        matrix1 = new Double[row][column];
    }

    public void setMatrix2(int row, int column) {
        matrix2 = new Double[row][column];
    }

    public void setMatrix3(int row, int column) {
        matrix3 = new Double[row][column];
    }

    public void setStoreMatrix(int row, int column) {
        storeMatrix = new Double[row][column];
    }

    public void MatrixMul() {
        setMatrix3(matrix1.length, matrix2[1].length);
        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[0].length; j++) {
                matrix3[i][j] = 0.0;
            }
        }
        if (matrix1[0].length != matrix2.length) {
            return;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[1].length; j++) {
                for (int n = 0; n < matrix2.length; n++) {
                    matrix3[i][j] += matrix1[i][n] * matrix2[n][j];
                }
            }
        }
        isThereAMatrix3 = true;
    }

    public void MatrixAdd() {
        int row = matrix1.length;
        int column = matrix1[0].length;
        if (row != matrix2.length || column != matrix2[0].length) {
            return;
        }
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix3[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        isThereAMatrix3 = true;

    }

    public void MatrixMinus() {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return;
        }
        ScalarMatrix2(-1);
        MatrixAdd();
        ScalarMatrix2(-1);
        isThereAMatrix3 = true;
    }

    public void ScalarMatrix1(double a) {
        if (isThereAMatrix1) {
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[0].length; j++) {
                    matrix1[i][j] = a * matrix1[i][j];
                }
            }
        }
    }

    public void ScalarMatrix2(double b) {
        if (isThereAMatrix2) {
            for (int i = 0; i < matrix2.length; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    matrix2[i][j] = b * matrix2[i][j];
                }
            }
        }
    }

    public Double[] getMatrix3(int i) {
        return matrix3[i];
    }//ㄤ龟⊿Τゲ璶糶硂ㄧΑ@@

    public void PutMatrix1() {
        int row = matrix3.length;
        int column = matrix3[0].length;
        setMatrix1(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix3[i], 0, matrix1[i], 0, column);
        }
        isThereAMatrix1=true;
    }

    public void PutMatrix2() {
        int row = matrix3.length;
        int column = matrix3[0].length;
        setMatrix2(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix3[i], 0, matrix2[i], 0, column);
        }
        isThereAMatrix2=true;
    }

    public void MatrixExchange() {
        int row = matrix1.length;
        int column = matrix1[0].length;
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix1[i], 0, matrix3[i], 0, column);
        }
        row = matrix2.length;
        column = matrix2[0].length;
        setMatrix1(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix2[i], 0, matrix1[i], 0, column);
        }
        row = matrix3.length;
        column = matrix3[0].length;
        setMatrix2(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix3[i], 0, matrix2[i], 0, column);
        }
    }

    public void TransportMatrix1() {
        int row = matrix1[0].length;
        int column = matrix1.length;
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix3[i][j] = matrix1[j][i];
            }
        }
        isThereAMatrix3 = true;
    }

    public void TransportMatrix2() {
        int row = matrix2[0].length;
        int column = matrix2.length;
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix3[i][j] = matrix2[j][i];
            }
        }
        isThereAMatrix3 = true;
    }

    private void identityMatrix3(int n) {
        setMatrix3(n, n);
        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[0].length; j++) {
                if (i == j) {
                    matrix3[i][j] = (double) 1;
                } else
                    matrix3[i][j] = (double) 0;
            }
        }
    }


    public double DeterminateMatrix1() {
        double det = 1.0;
        int n = matrix1.length;
        if (n != matrix1[0].length) {
            return 0;
        }
        setStoreMatrix(n, n);
        for (int i = 0; i < n; i++) {//盢matrix1じ狡籹storeMatrix
            System.arraycopy(matrix1[i], 0, storeMatrix[i], 0, n);
        }
        for (int i = 0; i < n - 1; i++) {//pivot row
            //тユ传row,狦pivot=0
            for (int index = i + 1; index < n; index++) {
                if (storeMatrix[i][i] == 0) {
                    det *= -1;
                    RowExchange(i, index);
                } else {
                    break;
                }
            }
            if (storeMatrix[i][i] == 0) {
                return 0;
            }//场ユ传筁pivot临琌0杠detゲ=0
            for (int j = i + 1; j < n; j++) {//other row
                double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                for (int k = 0; k < n; k++) {
                    storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            det *= storeMatrix[i][i];
        }
        det = Math.round(det * 1000000) / 1000000;
        return det;
    }

    public double DeterminateMatrix2() {
        double det = 1.0;
        int n = matrix2.length;
        if (n != matrix2[0].length) {
            return 0;
        }
        setStoreMatrix(n, n);
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix2[i], 0, storeMatrix[i], 0, n);
        }
        for (int i = 0; i < n - 1; i++) {//pivot row
            //тユ传row,狦pivot=0
            for (int index = i + 1; index < n; index++) {
                if (storeMatrix[i][i] == 0) {
                    det *= -1;
                    RowExchange(i, index);
                } else {
                    break;
                }
            }
            if (storeMatrix[i][i] == 0) {
                return 0;
            }//场ユ传筁pivot临琌0杠detゲ=0
            for (int j = i + 1; j < n; j++) {//other row
                double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                for (int k = 0; k < n; k++) {
                    storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            det *= storeMatrix[i][i];
        }
        det = Math.round(det * 1000000) / 1000000;
        return det;
    }

    private void RowExchange(int r1, int r2) {
        double temp;
        for (int i = 0; i < storeMatrix.length; i++) {
            temp = storeMatrix[r1][i];
            storeMatrix[r1][i] = storeMatrix[r2][i];
            storeMatrix[r2][i] = temp;
        }
    }

    private void RowExchangeMatrix3(int r1, int r2) {
        double temp;
        for (int i = 0; i < matrix3[0].length; i++) {
            temp = matrix3[r1][i];
            matrix3[r1][i] = matrix3[r2][i];
            matrix3[r2][i] = temp;
        }
    }

    public void InverseMatrix1() {
        if (DeterminateMatrix1() != 0) {
            int n = matrix1.length;
            identityMatrix3(n);
            for (int i = 0; i < n; i++) {
                System.arraycopy(matrix1[i], 0, storeMatrix[i], 0, n);
            }
            for (int i = 0; i < n - 1; i++) {//pivot row
                //тユ传row,狦pivot=0
                for (int index = i + 1; index < n; index++) {
                    if (storeMatrix[i][i] == 0) {
                        RowExchange(i, index);
                        RowExchangeMatrix3(i, index);
                    } else {
                        break;
                    }
                }
                for (int j = i + 1; j < n; j++) {//other row
                    double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                    for (int k = 0; k < n; k++) {
                        storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                        matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                    }
                }
            }

            for (int i = n - 1; i > 0; i--) {//pivot row
                //тユ传row,狦pivot=0
                for (int j = i - 1; j >= 0; j--) {//other row
                    double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                    for (int k = 0; k < n; k++) {
                        storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                        matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix3[i][j] = (double) Math.round(matrix3[i][j] * 1000000) / 1000000;
                    matrix3[i][j] = matrix3[i][j] / storeMatrix[i][i];
                }
            }
        }
    }

    public void InverseMatrix2() {
        if (DeterminateMatrix2() != 0) {
            int n = matrix2.length;
            identityMatrix3(n);
            for (int i = 0; i < n; i++) {
                System.arraycopy(matrix2[i], 0, storeMatrix[i], 0, n);
            }
            for (int i = 0; i < n - 1; i++) {//pivot row
                //тユ传row,狦pivot=0
                for (int index = i + 1; index < n; index++) {
                    if (storeMatrix[i][i] == 0) {
                        RowExchange(i, index);
                        RowExchangeMatrix3(i, index);
                    } else {
                        break;
                    }
                }
                for (int j = i + 1; j < n; j++) {//other row
                    double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                    for (int k = 0; k < n; k++) {
                        storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                        matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                    }
                }
            }

            for (int i = n - 1; i > 0; i--) {//pivot row
                //тユ传row,狦pivot=0
                for (int j = i - 1; j >= 0; j--) {//other row
                    double scalar = storeMatrix[j][i] / storeMatrix[i][i];
                    for (int k = 0; k < n; k++) {
                        storeMatrix[j][k] = storeMatrix[j][k] - scalar * storeMatrix[i][k];
                        matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix3[i][j] = (double) Math.round(matrix3[i][j] * 1000000) / 1000000;
                    matrix3[i][j] = matrix3[i][j] / storeMatrix[i][i];
                }
            }
        }
    }

    public void ReduceRowEchelonMatrix1() {
        rankOfMatrix1=0;
        int row = matrix1.length;
        int column = matrix1[0].length;
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix1[i], 0, matrix3[i], 0, column);
        }
        int pivot = 0;
        for (int i = 0; i < row - 1; i++) {
            for (int j = i; j < row; j++) {
                if (matrix3[j][pivot] != 0) {
                    if (i != j) {
                        RowExchangeMatrix3(i, j);
                    }
                    for (int k = i + 1; k < row; k++) {
                        double scalar = matrix3[k][pivot] / matrix3[i][pivot];
                        for (int l = 0; l < column; l++) {
                            matrix3[k][l] = matrix3[k][l] - scalar * matrix3[i][l];
                            matrix3[k][l] = (double) Math.round(matrix3[k][l] * 1000000) / 1000000;
                        }
                    }
                    double DivPivot = matrix3[i][pivot];
                    for (int k = 0; k < column; k++) {
                        matrix3[i][k] /= DivPivot;
                    }
                    pivot++;
                    rankOfMatrix1++;
                    break;
                }
                if (j == row - 1) {
                    pivot++;
                    i--;
                    if (pivot >= column) {
                        break;
                    }
                }
            }
            if (pivot >= column) {
                break;
            }
        }//à痻皚
        double div = 0;
        for (int i = 0; i < column; i++) {
            if (matrix3[row - 1][i] != 0) {
                div = matrix3[row - 1][i];
                break;
            }
        }
        if (div != 0) {
            for (int i = 0; i < column; i++) {
                matrix3[row - 1][i] /= div;
            }
        }
        for (int i = row - 1; i > 0; i--) {
            int pivot1 = 0;
            for (int j = 0; j < column; j++) {
                if (matrix3[i][j] != 0) {
                    pivot1 = j;
                    break;
                }
            }
            for (int j = i - 1; j >= 0; j--) {
                double scalar = matrix3[j][pivot1];
                for (int k = 0; k < column; k++) {
                    matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix3[i][j] = (double) Math.round(matrix3[i][j] * 1000000) / 1000000;
            }
        }
    }

    public void ReduceRowEchelonMatrix2() {
        rankOfMatrix2=0;
        int row = matrix2.length;
        int column = matrix2[0].length;
        setMatrix3(row, column);
        for (int i = 0; i < row; i++) {
            System.arraycopy(matrix2[i], 0, matrix3[i], 0, column);
        }
        int pivot = 0;
        for (int i = 0; i < row - 1; i++) {
            for (int j = i; j < row; j++) {
                if (matrix3[j][pivot] != 0) {
                    if (i != j) {
                        RowExchangeMatrix3(i, j);
                    }
                    for (int k = i + 1; k < row; k++) {
                        double scalar = matrix3[k][pivot] / matrix3[i][pivot];
                        for (int l = 0; l < column; l++) {
                            matrix3[k][l] = matrix3[k][l] - scalar * matrix3[i][l];
                            matrix3[k][l] = (double) Math.round(matrix3[k][l] * 1000000) / 1000000;
                        }
                    }
                    double DivPivot = matrix3[i][pivot];
                    for (int k = 0; k < column; k++) {
                        matrix3[i][k] /= DivPivot;
                    }
                    pivot++;
                    rankOfMatrix2++;
                    break;
                }
                if (j == row - 1) {
                    pivot++;
                    i--;
                    if (pivot >= column) {
                        break;
                    }
                }
            }
            if (pivot >= column) {
                break;
            }
        }//à痻皚
        double div = 0;
        for (int i = 0; i < column; i++) {
            if (matrix3[row - 1][i] != 0) {
                div = matrix3[row - 1][i];
                break;
            }
        }
        if (div != 0) {
            for (int i = 0; i < column; i++) {
                matrix3[row - 1][i] /= div;
            }
        }
        for (int i = row - 1; i > 0; i--) {
            int pivot1 = 0;
            for (int j = 0; j < column; j++) {
                if (matrix3[i][j] != 0) {
                    pivot1 = j;
                    break;
                }
            }
            for (int j = i - 1; j >= 0; j--) {
                double scalar = matrix3[j][pivot1];
                for (int k = 0; k < column; k++) {
                    matrix3[j][k] = matrix3[j][k] - scalar * matrix3[i][k];
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix3[i][j] = (double) Math.round(matrix3[i][j] * 1000000) / 1000000;
            }
        }
    }
    public int RankOfMatrix1(){
        ReduceRowEchelonMatrix1();
        int LastRow =matrix3.length-1;
        for(int i=0;i<matrix3[0].length;i++){
            if(matrix3[LastRow][i]!=0){
                rankOfMatrix1++;
                break;
            }
        }
        return rankOfMatrix1 ;
    }
    public int RankOfMatrix2(){
        ReduceRowEchelonMatrix2();
        int LastRow =matrix3.length-1;
        for(int i=0;i<matrix3[0].length;i++){
            if(matrix3[LastRow][i]!=0){
                rankOfMatrix2++;
                break;
            }
        }
        return rankOfMatrix2 ;
    }
}
