CREATE TABLE comment
(
    id         BIGINT AUTO_INCREMENT    NOT NULL,
    crated_at  datetime                 NOT NULL,
    updated_at datetime                 NOT NULL,
    content    TEXT                     NOT NULL,
    member_id  BIGINT                   NOT NULL,
    post_id    BIGINT                   NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE event
(
    id                   BIGINT AUTO_INCREMENT              NOT NULL,
    crated_at            datetime                           NOT NULL,
    updated_at           datetime                           NOT NULL,
    event_type           enum ('FOLLOW', 'LIKE', 'COMMENT') NOT NULL,
    post_id              BIGINT                             NULL,
    comment_id           BIGINT                             NULL,
    initiating_member_id BIGINT                             NULL,
    affected_member_id   BIGINT                             NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE follow
(
    follower_id  BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    CONSTRAINT PRIMARY KEY (follower_id, following_id)
);

CREATE TABLE like_post
(
    member_id BIGINT NOT NULL,
    post_id   BIGINT NOT NULL,
    CONSTRAINT PRIMARY KEY (member_id, post_id)
);

CREATE TABLE member
(
    id                       BIGINT AUTO_INCREMENT NOT NULL,
    crated_at                datetime              NOT NULL,
    updated_at               datetime              NOT NULL,
    first_name               VARCHAR(255)          NOT NULL,
    last_name                VARCHAR(255)          NOT NULL,
    email                    VARCHAR(255)          NOT NULL,
    password                 VARCHAR(255)          NOT NULL,
    birth_date               date                  NOT NULL,
    enabled                  BIT(1)                NOT NULL,
    profile_picture_location VARCHAR(255)          NULL,
    bio                      MEDIUMTEXT              NULL,
    location                 VARCHAR(255)          NULL,
    profession               VARCHAR(255)          NULL,
    role_id                  BIGINT                NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE picture
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    data MEDIUMBLOB            NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE post
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    crated_at      datetime              NOT NULL,
    updated_at     datetime              NOT NULL,
    image_location VARCHAR(255)          NULL,
    content        MEDIUMTEXT            NOT NULL,
    member_id      BIGINT                NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE report
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    crated_at           datetime              NOT NULL,
    updated_at          datetime              NOT NULL,
    report_reason       TEXT                  NOT NULL,
    reporting_member_id BIGINT                NOT NULL,
    reported_post_id    BIGINT                NOT NULL,
    report_processed    BIT(1)                NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    crated_at   datetime                NOT NULL,
    updated_at  datetime                NOT NULL,
    member_role enum ('USER', 'ADMIN')  NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE token
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    crated_at  datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    type       enum ('BEARER')       NOT NULL,
    token      VARCHAR(255)          NOT NULL,
    is_valid   BIT(1)                NOT NULL,
    member_id  BIGINT                NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);

ALTER TABLE member
    ADD CONSTRAINT uc_member_email UNIQUE (email);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_member_role UNIQUE (member_role);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_POST FOREIGN KEY (post_id) REFERENCES post (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_AFFECTED_MEMBER FOREIGN KEY (affected_member_id) REFERENCES member (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_COMMENT FOREIGN KEY (comment_id) REFERENCES comment (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_INITIATING_MEMBER FOREIGN KEY (initiating_member_id) REFERENCES member (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_POST FOREIGN KEY (post_id) REFERENCES post (id);

ALTER TABLE member
    ADD CONSTRAINT FK_MEMBER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_REPORTED_POST FOREIGN KEY (reported_post_id) REFERENCES post (id);

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_REPORTING_MEMBER FOREIGN KEY (reporting_member_id) REFERENCES member (id);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE follow
    ADD CONSTRAINT fk_follow_on_follower FOREIGN KEY (follower_id) REFERENCES member (id);

ALTER TABLE follow
    ADD CONSTRAINT fk_follow_on_following FOREIGN KEY (following_id) REFERENCES member (id);

ALTER TABLE like_post
    ADD CONSTRAINT fk_like_post_on_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE like_post
    ADD CONSTRAINT fk_like_post_on_post FOREIGN KEY (post_id) REFERENCES post (id);