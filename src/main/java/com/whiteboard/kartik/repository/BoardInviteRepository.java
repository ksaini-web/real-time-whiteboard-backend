package com.whiteboard.kartik.repository;

import com.whiteboard.kartik.model.BoardInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardInviteRepository
        extends JpaRepository<BoardInvite, Long> {

    List<BoardInvite>
    findByInvitedEmailAndAccepted(
            String invitedEmail,
            boolean accepted
    );
}