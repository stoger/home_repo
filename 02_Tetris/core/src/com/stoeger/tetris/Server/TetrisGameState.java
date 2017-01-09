package com.stoeger.tetris.Server;

import com.stoeger.tetris.cmd.PollRequest;

public abstract class TetrisGameState {
	public abstract PollRequest start(PollRequest request);

	public abstract PollRequest run(PollRequest request);

	public abstract PollRequest stop(PollRequest request);

	public abstract PollRequest pause(PollRequest request);

	public abstract PollRequest lose(PollRequest request);

	public abstract PollRequest win(PollRequest request);
}

