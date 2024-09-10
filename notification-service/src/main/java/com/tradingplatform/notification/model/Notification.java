package com.tradingplatform.notification.model;

import java.sql.Timestamp;

import com.tradingplatform.common.enums.NotificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	private long notificationId;

	private long userId;

	private String type;

	private String message;

	private NotificationStatus status;

	private Timestamp date;

}
