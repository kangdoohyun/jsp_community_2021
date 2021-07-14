package com.jhs.exam.exam2.service;

import java.util.List;

import com.jhs.exam.exam2.container.Container;
import com.jhs.exam.exam2.dto.Board;
import com.jhs.exam.exam2.repository.BoardRepository;

public class BoardService {
	private BoardRepository boardRepository = Container.boardRepository;

	public List<Board> getBoards() {
		return boardRepository.getBoards();
	}

}
