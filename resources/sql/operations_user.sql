-- name: get-user
SELECT cip, name, surname FROM "user" WHERE cip = :cip;

--name: update-token-time-db!
UPDATE authentication_token SET time = now() WHERE token = :token;

-- name: insert-user!
INSERT INTO "user"(cip, name, surname) VALUES(:cip, :name, :surname);

-- name: login-db!
INSERT INTO authentication_token(cip, token, time) VALUES(:cip, :token, now());

-- name: logout-using-cip-db!
DELETE FROM authentication_token WHERE cip = :cip;

-- name: logout-using-token-db!
DELETE FROM authentication_token WHERE token = :token;

-- name: get-logged-user-db
SELECT "user".cip, name, surname, time FROM "user"
    INNER JOIN authentication_token ON "user".cip = authentication_token.cip
    WHERE token = :token
