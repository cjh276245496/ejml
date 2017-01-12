/*
 * Copyright (c) 2009-2017, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ejml.dense.block;

import org.ejml.data.DMatrixBlock_F64;
import org.ejml.data.DMatrixRow_F64;
import org.ejml.dense.row.CommonOps_R64;
import org.ejml.dense.row.RandomMatrices_R64;

import java.util.Random;


/**
 * Compare block against other transpose for DMatrixRow_F64
 *
 *  @author Peter Abeles
 */
public class BenchmarkBlockTranspose {

    static Random rand = new Random(234);

    public static long transposeDenseInPlace(DMatrixRow_F64 mat , int numTrials) {

        long prev = System.currentTimeMillis();

        for( int i = 0; i < numTrials; i++ ) {
            CommonOps_R64.transpose(mat);
        }
        long curr = System.currentTimeMillis();

        return curr-prev;
    }

    public static long transposeDense(DMatrixRow_F64 mat , int numTrials) {


        DMatrixRow_F64 tran = new DMatrixRow_F64(mat.numCols,mat.numRows);

        long prev = System.currentTimeMillis();

        for( int i = 0; i < numTrials; i++ ) {
            CommonOps_R64.transpose(mat,tran);
        }
        long curr = System.currentTimeMillis();

        return curr-prev;
    }

    public static long transposeBlock(DMatrixRow_F64 mat , int numTrials) {

        DMatrixBlock_F64 A = new DMatrixBlock_F64(mat.numRows,mat.numCols);
        DMatrixBlock_F64 A_t = new DMatrixBlock_F64(mat.numCols,mat.numRows);

        MatrixOps_B64.convert(mat,A);

        long prev = System.currentTimeMillis();

        for( int i = 0; i < numTrials; i++ ) {
            MatrixOps_B64.transpose(A,A_t);
        }
        long curr = System.currentTimeMillis();

        return curr-prev;
    }

    public static void main( String args[] ) {

        DMatrixRow_F64 A = RandomMatrices_R64.createRandom(5000,5000,rand);

        int N = 5;

        System.out.println("In place  : "+transposeDenseInPlace(A,N));
        System.out.println("Standard  : "+transposeDense(A,N));
        System.out.println("Block     : "+transposeBlock(A,N));
    }
}