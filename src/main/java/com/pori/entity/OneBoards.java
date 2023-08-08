package com.pori.entity;

import com.pori.constrant.RoomStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //엔티티 클래스로 정의
@Table(name="one_board") //테이블 이름 지정
@Getter
@Setter
@ToString
public class OneBoards extends BaseEntity {
	
	@Id
	@Column(name="room_id") //테이블로 생설될때 컬럼이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) // 기본키를 자동으로 생성
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private RoomStatus roomStatus;
	
	public void closeRoom() {
		this.roomStatus = roomStatus.CLOSE;
	}
	
	private String sessionId;
}
