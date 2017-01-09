package com.stoeger.tetris.cmd;

import java.io.Serializable;


public interface Request extends Serializable {

    public Request execute(TetrisServer srv) throws Exception;

    public Request execute(TetrisClient cln) throws Exception;

}
