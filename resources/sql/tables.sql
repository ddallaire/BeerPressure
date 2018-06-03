/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2018-05-30 5:34:06 PM                        */
/*==============================================================*/


DROP INDEX IF EXISTS beer_pk;

DROP TABLE IF EXISTS beer CASCADE;

DROP INDEX IF EXISTS user_beer_activity_fk;

DROP INDEX IF EXISTS beer_beer_activity_fk;

DROP INDEX IF EXISTS beer_activity_pk;

DROP TABLE IF EXISTS beer_activity CASCADE;

DROP INDEX IF EXISTS belongs_to2_fk;

DROP INDEX IF EXISTS belongs_to_fk;

DROP INDEX IF EXISTS belongs_to_pk;

DROP TABLE IF EXISTS beer_brewery CASCADE;

DROP INDEX IF EXISTS can_write_fk;

DROP INDEX IF EXISTS review_pk;

DROP TABLE IF EXISTS beer_review CASCADE;

DROP INDEX IF EXISTS beer_review_comment_fk;

DROP INDEX IF EXISTS user_beer_review_comment_fk;

DROP INDEX IF EXISTS beer_review_comment_pk;

DROP TABLE IF EXISTS beer_review_comment CASCADE;

DROP INDEX IF EXISTS can_be_associated2_fk;

DROP INDEX IF EXISTS can_be_associated_fk;

DROP INDEX IF EXISTS can_be_associated_pk;

DROP TABLE IF EXISTS beer_tag CASCADE;

DROP INDEX IF EXISTS can_be_wished_for2_fk;

DROP INDEX IF EXISTS can_be_wished_for_fk;

DROP INDEX IF EXISTS can_be_wished_for_pk;

DROP TABLE IF EXISTS beer_user_drank CASCADE;

DROP INDEX IF EXISTS beer_user_rating2_fk;

DROP INDEX IF EXISTS beer_user_rating_fk;

DROP INDEX IF EXISTS beer_user_rating_pk;

DROP TABLE IF EXISTS beer_user_rating CASCADE;

DROP INDEX IF EXISTS brewery_pk;

DROP TABLE IF EXISTS brewery CASCADE;

DROP INDEX IF EXISTS user_brewery_review_fk;

DROP INDEX IF EXISTS review_comment_pk;

DROP TABLE IF EXISTS brewery_review CASCADE;

DROP INDEX IF EXISTS user_brewery_review_comment_fk;

DROP INDEX IF EXISTS brewery_review_comment_fk;

DROP INDEX IF EXISTS brewery_review_comment_pk;

DROP TABLE IF EXISTS brewery_review_comment CASCADE;

DROP INDEX IF EXISTS brewery_review_user_thumbsup2_fk;

DROP INDEX IF EXISTS brewery_review_user_thumbsup_fk;

DROP INDEX IF EXISTS brewery_review_user_thumbsup_pk;

DROP TABLE IF EXISTS brewery_review_user_thumbsup CASCADE;

DROP INDEX IF EXISTS brewery_user_rating2_fk;

DROP INDEX IF EXISTS brewery_user_rating_fk;

DROP INDEX IF EXISTS brewery_user_rating_pk;

DROP TABLE IF EXISTS brewery_user_rating CASCADE;

DROP INDEX IF EXISTS comment_brewery_activity_fk;

DROP INDEX IF EXISTS comment_comment_activity_fk;

DROP INDEX IF EXISTS comment_activity_pk;

DROP TABLE IF EXISTS comment_activity CASCADE;

DROP INDEX IF EXISTS brewery_brewery_activity_fk;

DROP INDEX IF EXISTS review_review_activity_fk;

DROP INDEX IF EXISTS review_activity_pk;

DROP TABLE IF EXISTS review_activity CASCADE;

DROP INDEX IF EXISTS beer_review_user_thumbsup2_fk;

DROP INDEX IF EXISTS beer_review_user_thumbsup_fk;

DROP INDEX IF EXISTS beer_review_user_thumbsup_pk;

DROP TABLE IF EXISTS beer_review_user_thumbsup CASCADE;

DROP INDEX IF EXISTS tag_pk;

DROP TABLE IF EXISTS tag CASCADE;

DROP INDEX IF EXISTS can_also_have2_fk;

DROP INDEX IF EXISTS can_also_have_fk;

DROP INDEX IF EXISTS can_also_have_pk;

DROP TABLE IF EXISTS tag_brewery CASCADE;

DROP INDEX IF EXISTS user_pk;

DROP TABLE IF EXISTS "user" CASCADE;

DROP INDEX IF EXISTS user_comment_activity2_fk;

DROP INDEX IF EXISTS user_comment_activity_fk;

DROP INDEX IF EXISTS user_comment_activity_pk;

DROP TABLE IF EXISTS user_comment_activity CASCADE;

DROP INDEX IF EXISTS user_follow2_fk;

DROP INDEX IF EXISTS user_follow_fk;

DROP INDEX IF EXISTS user_follow_pk;

DROP TABLE IF EXISTS user_follow CASCADE;

DROP INDEX IF EXISTS user_review_activity2_fk;

DROP INDEX IF EXISTS user_review_activity_fk;

DROP INDEX IF EXISTS user_review_activity_pk;

DROP TABLE IF EXISTS user_review_activity CASCADE;

DROP SEQUENCE IF EXISTS s_beer;

DROP SEQUENCE IF EXISTS s_beer_activity;

DROP SEQUENCE IF EXISTS s_beer_review;

DROP SEQUENCE IF EXISTS s_beer_review_comment;

DROP SEQUENCE IF EXISTS s_brewery;

DROP SEQUENCE IF EXISTS s_brewery_review;

DROP SEQUENCE IF EXISTS s_brewery_review_comment;

DROP SEQUENCE IF EXISTS s_comment_activity;

DROP SEQUENCE IF EXISTS s_review_activity;

DROP SEQUENCE IF EXISTS s_tag;

CREATE SEQUENCE s_beer;

CREATE SEQUENCE s_beer_activity;

CREATE SEQUENCE s_beer_review;

CREATE SEQUENCE s_beer_review_comment;

CREATE SEQUENCE s_brewery;

CREATE SEQUENCE s_brewery_review;

CREATE SEQUENCE s_brewery_review_comment;

CREATE SEQUENCE s_comment_activity;

CREATE SEQUENCE s_review_activity;

CREATE SEQUENCE s_tag;

/*==============================================================*/
/* Table: beer                                                  */
/*==============================================================*/
CREATE TABLE beer (
   id_beer              SERIAL NOT NULL,
   name                 VARCHAR(256)         NOT NULL,
   description          VARCHAR(1024)        NOT NULL,
   ibu                  INT4                 NOT NULL,
   alcohol_percent       FLOAT8               NOT NULL,
   image_path           VARCHAR(1024)         NULL,
   CONSTRAINT pk_beer PRIMARY KEY (id_beer)
);

/*==============================================================*/
/* Index: beer_pk                                               */
/*==============================================================*/
CREATE UNIQUE INDEX beer_pk ON beer (
id_beer
);

/*==============================================================*/
/* Table: beer_activity                                         */
/*==============================================================*/
CREATE TABLE beer_activity (
   id_beer_activity     SERIAL NOT NULL,
   id_beer              INT4                 NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   CONSTRAINT pk_beer_activity PRIMARY KEY (id_beer_activity)
);

/*==============================================================*/
/* Index: beer_activity_pk                                      */
/*==============================================================*/
CREATE UNIQUE INDEX beer_activity_pk ON beer_activity (
id_beer_activity
);

/*==============================================================*/
/* Index: beer_beer_activity_fk                                 */
/*==============================================================*/
CREATE  INDEX beer_beer_activity_fk ON beer_activity (
id_beer
);

/*==============================================================*/
/* Index: user_beer_activity_fk                                 */
/*==============================================================*/
CREATE  INDEX user_beer_activity_fk ON beer_activity (
cip
);

/*==============================================================*/
/* Table: beer_brewery                                          */
/*==============================================================*/
CREATE TABLE beer_brewery (
   id_brewery           INT4                 NOT NULL,
   id_beer              INT4                 NOT NULL,
   CONSTRAINT pk_beer_brewery PRIMARY KEY (id_brewery, id_beer)
);

/*==============================================================*/
/* Index: belongs_to_pk                                         */
/*==============================================================*/
CREATE UNIQUE INDEX belongs_to_pk ON beer_brewery (
id_brewery,
id_beer
);

/*==============================================================*/
/* Index: belongs_to_fk                                         */
/*==============================================================*/
CREATE  INDEX belongs_to_fk ON beer_brewery (
id_brewery
);

/*==============================================================*/
/* Index: belongs_to2_fk                                        */
/*==============================================================*/
CREATE  INDEX belongs_to2_fk ON beer_brewery (
id_beer
);

/*==============================================================*/
/* Table: beer_review                                           */
/*==============================================================*/
CREATE TABLE beer_review (
   id_beer_review       SERIAL NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   id_beer              INT4                 NOT NULL,
   title                VARCHAR(256)         NOT NULL,
   content              VARCHAR(1024)        NOT NULL,
   image_path           VARCHAR(1024)         NULL,
   rating               FLOAT8               NOT NULL,
   time                 TIMESTAMPTZ          NOT NULL,
   CONSTRAINT pk_beer_review PRIMARY KEY (id_beer_review)
);

/*==============================================================*/
/* Index: review_pk                                             */
/*==============================================================*/
CREATE UNIQUE INDEX review_pk ON beer_review (
id_beer_review
);

/*==============================================================*/
/* Index: can_write_fk                                          */
/*==============================================================*/
CREATE  INDEX can_write_fk ON beer_review (
cip
);

/*==============================================================*/
/* Table: beer_review_comment                                   */
/*==============================================================*/
CREATE TABLE beer_review_comment (
   id_beer_review_comment SERIAL NOT NULL,
   id_beer_review       INT4                 NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   content              VARCHAR(1024)        NOT NULL,
   time                 TIMESTAMPTZ          NOT NULL,
   CONSTRAINT pk_beer_review_comment PRIMARY KEY (id_beer_review_comment)
);

/*==============================================================*/
/* Index: beer_review_comment_pk                                */
/*==============================================================*/
CREATE UNIQUE INDEX beer_review_comment_pk ON beer_review_comment (
id_beer_review_comment
);

/*==============================================================*/
/* Index: user_beer_review_comment_fk                           */
/*==============================================================*/
CREATE  INDEX user_beer_review_comment_fk ON beer_review_comment (
cip
);

/*==============================================================*/
/* Index: beer_review_comment_fk                                */
/*==============================================================*/
CREATE  INDEX beer_review_comment_fk ON beer_review_comment (
id_beer_review
);

/*==============================================================*/
/* Table: beer_tag                                              */
/*==============================================================*/
CREATE TABLE beer_tag (
   id_beer              INT4                 NOT NULL,
   id_tag               INT4                 NOT NULL,
   CONSTRAINT pk_beer_tag PRIMARY KEY (id_beer, id_tag)
);

/*==============================================================*/
/* Index: can_be_associated_pk                                  */
/*==============================================================*/
CREATE UNIQUE INDEX can_be_associated_pk ON beer_tag (
id_beer,
id_tag
);

/*==============================================================*/
/* Index: can_be_associated_fk                                  */
/*==============================================================*/
CREATE  INDEX can_be_associated_fk ON beer_tag (
id_beer
);

/*==============================================================*/
/* Index: can_be_associated2_fk                                 */
/*==============================================================*/
CREATE  INDEX can_be_associated2_fk ON beer_tag (
id_tag
);

/*==============================================================*/
/* Table: beer_user_drank                                       */
/*==============================================================*/
CREATE TABLE beer_user_drank (
   id_beer              INT4                 NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   CONSTRAINT pk_beer_user_drank PRIMARY KEY (id_beer, cip)
);

/*==============================================================*/
/* Index: can_be_wished_for_pk                                  */
/*==============================================================*/
CREATE UNIQUE INDEX can_be_wished_for_pk ON beer_user_drank (
id_beer,
cip
);

/*==============================================================*/
/* Index: can_be_wished_for_fk                                  */
/*==============================================================*/
CREATE  INDEX can_be_wished_for_fk ON beer_user_drank (
id_beer
);

/*==============================================================*/
/* Index: can_be_wished_for2_fk                                 */
/*==============================================================*/
CREATE  INDEX can_be_wished_for2_fk ON beer_user_drank (
cip
);

/*==============================================================*/
/* Table: beer_user_rating                                      */
/*==============================================================*/
CREATE TABLE beer_user_rating (
   id_beer              INT4                 NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   rating               FLOAT8               NOT NULL,
   CONSTRAINT pk_beer_user_rating PRIMARY KEY (id_beer, cip)
);

/*==============================================================*/
/* Index: beer_user_rating_pk                                   */
/*==============================================================*/
CREATE UNIQUE INDEX beer_user_rating_pk ON beer_user_rating (
id_beer,
cip
);

/*==============================================================*/
/* Index: beer_user_rating_fk                                   */
/*==============================================================*/
CREATE  INDEX beer_user_rating_fk ON beer_user_rating (
id_beer
);

/*==============================================================*/
/* Index: beer_user_rating2_fk                                  */
/*==============================================================*/
CREATE  INDEX beer_user_rating2_fk ON beer_user_rating (
cip
);

/*==============================================================*/
/* Table: brewery                                               */
/*==============================================================*/
CREATE TABLE brewery (
   id_brewery           SERIAL NOT NULL,
   name                 VARCHAR(256)         NOT NULL,
   description          VARCHAR(1024)        NOT NULL,
   image_path           VARCHAR(1024)         NULL,
   CONSTRAINT pk_brewery PRIMARY KEY (id_brewery)
);

/*==============================================================*/
/* Index: brewery_pk                                            */
/*==============================================================*/
CREATE UNIQUE INDEX brewery_pk ON brewery (
id_brewery
);

/*==============================================================*/
/* Table: brewery_review                                        */
/*==============================================================*/
CREATE TABLE brewery_review (
   id_brewery_review    SERIAL NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   id_brewery           INT4                 NOT NULL,
   title                VARCHAR(256)         NOT NULL,
   content              VARCHAR(1024)        NOT NULL,
   image_path           VARCHAR(1024)         NULL,
   rating               FLOAT8               NOT NULL,
   time                 TIMESTAMPTZ          NOT NULL,
   CONSTRAINT pk_brewery_review PRIMARY KEY (id_brewery_review)
);

/*==============================================================*/
/* Index: review_comment_pk                                     */
/*==============================================================*/
CREATE UNIQUE INDEX review_comment_pk ON brewery_review (
id_brewery_review
);

/*==============================================================*/
/* Index: user_brewery_review_fk                                */
/*==============================================================*/
CREATE  INDEX user_brewery_review_fk ON brewery_review (
cip
);

/*==============================================================*/
/* Table: brewery_review_comment                                */
/*==============================================================*/
CREATE TABLE brewery_review_comment (
   id_brewery_review_comment SERIAL NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   id_brewery_review    INT4                 NOT NULL,
   content              VARCHAR(1024)        NOT NULL,
   time                 TIMESTAMPTZ          NOT NULL,
   CONSTRAINT pk_brewery_review_comment PRIMARY KEY (id_brewery_review_comment)
);

/*==============================================================*/
/* Index: brewery_review_comment_pk                             */
/*==============================================================*/
CREATE UNIQUE INDEX brewery_review_comment_pk ON brewery_review_comment (
id_brewery_review_comment
);

/*==============================================================*/
/* Index: brewery_review_comment_fk                             */
/*==============================================================*/
CREATE  INDEX brewery_review_comment_fk ON brewery_review_comment (
id_brewery_review
);

/*==============================================================*/
/* Index: user_brewery_review_comment_fk                        */
/*==============================================================*/
CREATE  INDEX user_brewery_review_comment_fk ON brewery_review_comment (
cip
);

/*==============================================================*/
/* Table: brewery_review_user_thumbsup                           */
/*==============================================================*/
CREATE TABLE brewery_review_user_thumbsup (
   cip                  CHAR(8)              NOT NULL,
   id_brewery_review    INT4                 NOT NULL,
   CONSTRAINT pk_brewery_review_user_thumbsup PRIMARY KEY (cip, id_brewery_review)
);

/*==============================================================*/
/* Index: brewery_review_user_thumbsup_pk                        */
/*==============================================================*/
CREATE UNIQUE INDEX brewery_review_user_thumbsup_pk ON brewery_review_user_thumbsup (
cip,
id_brewery_review
);

/*==============================================================*/
/* Index: brewery_review_user_thumbsup_fk                        */
/*==============================================================*/
CREATE  INDEX brewery_review_user_thumbsup_fk ON brewery_review_user_thumbsup (
cip
);

/*==============================================================*/
/* Index: brewery_review_user_thumbsup2_fk                       */
/*==============================================================*/
CREATE  INDEX brewery_review_user_thumbsup2_fk ON brewery_review_user_thumbsup (
id_brewery_review
);

/*==============================================================*/
/* Table: brewery_user_rating                                   */
/*==============================================================*/
CREATE TABLE brewery_user_rating (
   cip                  CHAR(8)              NOT NULL,
   id_brewery           INT4                 NOT NULL,
   rating               FLOAT8               NOT NULL,
   CONSTRAINT pk_brewery_user_rating PRIMARY KEY (cip, id_brewery)
);

/*==============================================================*/
/* Index: brewery_user_rating_pk                                */
/*==============================================================*/
CREATE UNIQUE INDEX brewery_user_rating_pk ON brewery_user_rating (
cip,
id_brewery
);

/*==============================================================*/
/* Index: brewery_user_rating_fk                                */
/*==============================================================*/
CREATE  INDEX brewery_user_rating_fk ON brewery_user_rating (
cip
);

/*==============================================================*/
/* Index: brewery_user_rating2_fk                               */
/*==============================================================*/
CREATE  INDEX brewery_user_rating2_fk ON brewery_user_rating (
id_brewery
);

/*==============================================================*/
/* Table: comment_activity                                      */
/*==============================================================*/
CREATE TABLE comment_activity (
   id_comment_activity  SERIAL NOT NULL,
   id_brewery_review_comment INT4                 NOT NULL,
   id_beer_review_comment INT4                 NOT NULL,
   CONSTRAINT pk_comment_activity PRIMARY KEY (id_comment_activity)
);

/*==============================================================*/
/* Index: comment_activity_pk                                   */
/*==============================================================*/
CREATE UNIQUE INDEX comment_activity_pk ON comment_activity (
id_comment_activity
);

/*==============================================================*/
/* Index: comment_comment_activity_fk                           */
/*==============================================================*/
CREATE  INDEX comment_comment_activity_fk ON comment_activity (
id_beer_review_comment
);

/*==============================================================*/
/* Index: comment_brewery_activity_fk                           */
/*==============================================================*/
CREATE  INDEX comment_brewery_activity_fk ON comment_activity (
id_brewery_review_comment
);

/*==============================================================*/
/* Table: review_activity                                       */
/*==============================================================*/
CREATE TABLE review_activity (
   id_review_activity   SERIAL NOT NULL,
   id_brewery_review    INT4                 NULL,
   id_beer_review       INT4                 NOT NULL,
   CONSTRAINT pk_review_activity PRIMARY KEY (id_review_activity)
);

/*==============================================================*/
/* Index: review_activity_pk                                    */
/*==============================================================*/
CREATE UNIQUE INDEX review_activity_pk ON review_activity (
id_review_activity
);

/*==============================================================*/
/* Index: review_review_activity_fk                             */
/*==============================================================*/
CREATE  INDEX review_review_activity_fk ON review_activity (
id_beer_review
);

/*==============================================================*/
/* Index: brewery_brewery_activity_fk                           */
/*==============================================================*/
CREATE  INDEX brewery_brewery_activity_fk ON review_activity (
id_brewery_review
);

/*==============================================================*/
/* Table: beer_review_user_thumbsup                                  */
/*==============================================================*/
CREATE TABLE beer_review_user_thumbsup (
   id_beer_review       INT4                 NOT NULL,
   cip                  CHAR(8)              NOT NULL,
   CONSTRAINT pk_beer_review_user_thumbsup PRIMARY KEY (id_beer_review, cip)
);

/*==============================================================*/
/* Index: beer_review_user_thumbsup_pk                               */
/*==============================================================*/
CREATE UNIQUE INDEX beer_review_user_thumbsup_pk ON beer_review_user_thumbsup (
id_beer_review,
cip
);

/*==============================================================*/
/* Index: beer_review_user_thumbsup_fk                               */
/*==============================================================*/
CREATE  INDEX beer_review_user_thumbsup_fk ON beer_review_user_thumbsup (
id_beer_review
);

/*==============================================================*/
/* Index: beer_review_user_thumbsup2_fk                              */
/*==============================================================*/
CREATE  INDEX beer_review_user_thumbsup2_fk ON beer_review_user_thumbsup (
cip
);

/*==============================================================*/
/* Table: tag                                                   */
/*==============================================================*/
CREATE TABLE tag (
   id_tag               SERIAL NOT NULL,
   name                 VARCHAR(256)         NOT NULL,
   CONSTRAINT pk_tag PRIMARY KEY (id_tag)
);

/*==============================================================*/
/* Index: tag_pk                                                */
/*==============================================================*/
CREATE UNIQUE INDEX tag_pk ON tag (
id_tag
);

/*==============================================================*/
/* Table: tag_brewery                                           */
/*==============================================================*/
CREATE TABLE tag_brewery (
   id_tag               INT4                 NOT NULL,
   id_brewery           INT4                 NOT NULL,
   CONSTRAINT pk_tag_brewery PRIMARY KEY (id_tag, id_brewery)
);

/*==============================================================*/
/* Index: can_also_have_pk                                      */
/*==============================================================*/
CREATE UNIQUE INDEX can_also_have_pk ON tag_brewery (
id_tag,
id_brewery
);

/*==============================================================*/
/* Index: can_also_have_fk                                      */
/*==============================================================*/
CREATE  INDEX can_also_have_fk ON tag_brewery (
id_tag
);

/*==============================================================*/
/* Index: can_also_have2_fk                                     */
/*==============================================================*/
CREATE  INDEX can_also_have2_fk ON tag_brewery (
id_brewery
);

/*==============================================================*/
/* Table: "user"                                                */
/*==============================================================*/
CREATE TABLE "user" (
   cip                  CHAR(8)              NOT NULL,
   name                 VARCHAR(256)         NOT NULL,
   surname              VARCHAR(256)         NOT NULL,
   CONSTRAINT pk_user PRIMARY KEY (cip)
);

/*==============================================================*/
/* Index: user_pk                                               */
/*==============================================================*/
CREATE UNIQUE INDEX user_pk ON "user" (
cip
);

/*==============================================================*/
/* Table: user_comment_activity                                 */
/*==============================================================*/
CREATE TABLE user_comment_activity (
   cip                  CHAR(8)              NOT NULL,
   id_comment_activity  INT4                 NOT NULL,
   CONSTRAINT pk_user_comment_activity PRIMARY KEY (cip, id_comment_activity)
);

/*==============================================================*/
/* Index: user_comment_activity_pk                              */
/*==============================================================*/
CREATE UNIQUE INDEX user_comment_activity_pk ON user_comment_activity (
cip,
id_comment_activity
);

/*==============================================================*/
/* Index: user_comment_activity_fk                              */
/*==============================================================*/
CREATE  INDEX user_comment_activity_fk ON user_comment_activity (
cip
);

/*==============================================================*/
/* Index: user_comment_activity2_fk                             */
/*==============================================================*/
CREATE  INDEX user_comment_activity2_fk ON user_comment_activity (
id_comment_activity
);

/*==============================================================*/
/* Table: user_follow                                           */
/*==============================================================*/
CREATE TABLE user_follow (
   follower_cip         CHAR(8)              NOT NULL,
   followee_cip         CHAR(8)              NOT NULL
);

/*==============================================================*/
/* Index: user_follow_pk                                        */
/*==============================================================*/
CREATE UNIQUE INDEX user_follow_pk ON user_follow (
follower_cip,
followee_cip
);

/*==============================================================*/
/* Index: user_follow_fk                                        */
/*==============================================================*/
CREATE  INDEX user_follow_fk ON user_follow (
follower_cip
);

/*==============================================================*/
/* Index: user_follow2_fk                                       */
/*==============================================================*/
CREATE  INDEX user_follow2_fk ON user_follow (
followee_cip
);

/*==============================================================*/
/* Table: user_review_activity                                  */
/*==============================================================*/
CREATE TABLE user_review_activity (
   cip                  CHAR(8)              NOT NULL,
   id_review_activity   INT4                 NOT NULL,
   CONSTRAINT pk_user_review_activity PRIMARY KEY (cip, id_review_activity)
);

/*==============================================================*/
/* Index: user_review_activity_pk                               */
/*==============================================================*/
CREATE UNIQUE INDEX user_review_activity_pk ON user_review_activity (
cip,
id_review_activity
);

/*==============================================================*/
/* Index: user_review_activity_fk                               */
/*==============================================================*/
CREATE  INDEX user_review_activity_fk ON user_review_activity (
cip
);

/*==============================================================*/
/* Index: user_review_activity2_fk                              */
/*==============================================================*/
CREATE  INDEX user_review_activity2_fk ON user_review_activity (
id_review_activity
);

ALTER TABLE beer_activity
   ADD CONSTRAINT fk_beer_act_beer_beer_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_activity
   ADD CONSTRAINT fk_beer_act_user_beer_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_brewery
   ADD CONSTRAINT fk_beer_bre_beer_brew_brewery FOREIGN KEY (id_brewery)
      REFERENCES brewery (id_brewery)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_brewery
   ADD CONSTRAINT fk_beer_bre_beer_brew_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review
   ADD CONSTRAINT fk_beer_rev_user_beer_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review
   ADD CONSTRAINT fk_beer_rev_beer_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review_comment
   ADD CONSTRAINT fk_beer_rev_beer_revi_beer_rev FOREIGN KEY (id_beer_review)
      REFERENCES beer_review (id_beer_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review_comment
   ADD CONSTRAINT fk_beer_rev_user_beer_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_tag
   ADD CONSTRAINT fk_beer_tag_beer_tag_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_tag
   ADD CONSTRAINT fk_beer_tag_beer_tag2_tag FOREIGN KEY (id_tag)
      REFERENCES tag (id_tag)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_user_drank
   ADD CONSTRAINT fk_beer_use_beer_user_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_user_drank
   ADD CONSTRAINT fk_beer_use_beer_user_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_user_rating
   ADD CONSTRAINT fk_beer_use_beer_user_beer FOREIGN KEY (id_beer)
      REFERENCES beer (id_beer)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_user_rating
   ADD CONSTRAINT fk_beer_use_beer_user_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review
   ADD CONSTRAINT fk_brewery__user_brew_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review
   ADD CONSTRAINT fk_brewery__brewery_brew_brewery FOREIGN KEY (id_brewery)
      REFERENCES brewery (id_brewery)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review_comment
   ADD CONSTRAINT fk_brewery__reference_brewery_ FOREIGN KEY (id_brewery_review)
      REFERENCES brewery_review (id_brewery_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review_comment
   ADD CONSTRAINT fk_brewery__user_brew_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review_user_thumbsup
   ADD CONSTRAINT fk_brewery__brewery_r_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_review_user_thumbsup
   ADD CONSTRAINT fk_brewery__brewery_r_brewery_ FOREIGN KEY (id_brewery_review)
      REFERENCES brewery_review (id_brewery_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_user_rating
   ADD CONSTRAINT fk_brewery__brewery_u_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE brewery_user_rating
   ADD CONSTRAINT fk_brewery__brewery_u_brewery FOREIGN KEY (id_brewery)
      REFERENCES brewery (id_brewery)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comment_activity
   ADD CONSTRAINT fk_comment__comment_b_brewery_ FOREIGN KEY (id_brewery_review_comment)
      REFERENCES brewery_review_comment (id_brewery_review_comment)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comment_activity
   ADD CONSTRAINT fk_comment__comment_c_beer_rev FOREIGN KEY (id_beer_review_comment)
      REFERENCES beer_review_comment (id_beer_review_comment)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE review_activity
   ADD CONSTRAINT fk_review_a_brewery_b_brewery_ FOREIGN KEY (id_brewery_review)
      REFERENCES brewery_review (id_brewery_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE review_activity
   ADD CONSTRAINT fk_review_a_review_re_beer_rev FOREIGN KEY (id_beer_review)
      REFERENCES beer_review (id_beer_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review_user_thumbsup
   ADD CONSTRAINT fk_review_u_review_us_beer_rev FOREIGN KEY (id_beer_review)
      REFERENCES beer_review (id_beer_review)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE beer_review_user_thumbsup
   ADD CONSTRAINT fk_review_u_review_us_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tag_brewery
   ADD CONSTRAINT fk_tag_brew_tag_brewe_tag FOREIGN KEY (id_tag)
      REFERENCES tag (id_tag)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tag_brewery
   ADD CONSTRAINT fk_tag_brew_tag_brewe_brewery FOREIGN KEY (id_brewery)
      REFERENCES brewery (id_brewery)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_comment_activity
   ADD CONSTRAINT fk_user_com_user_comm_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_comment_activity
   ADD CONSTRAINT fk_user_com_user_comm_comment_ FOREIGN KEY (id_comment_activity)
      REFERENCES comment_activity (id_comment_activity)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_follow
   ADD CONSTRAINT fk_user_fol_user_foll_user FOREIGN KEY (follower_cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_follow
   ADD CONSTRAINT fk_user_fol_user_followed_user FOREIGN KEY (followee_cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_review_activity
   ADD CONSTRAINT fk_user_rev_user_revi_user FOREIGN KEY (cip)
      REFERENCES "user" (cip)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_review_activity
   ADD CONSTRAINT fk_user_rev_user_revi_review_a FOREIGN KEY (id_review_activity)
      REFERENCES review_activity (id_review_activity)
      ON DELETE RESTRICT ON UPDATE RESTRICT;

