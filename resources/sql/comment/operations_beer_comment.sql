-- name: get-beer-review-comment
SELECT id_beer_review_comment, id_beer_review, "user".cip, name, surname, content, time FROM beer_review_comment
    INNER JOIN "user" ON "user".cip = beer_review_comment.cip
    WHERE id_beer_review_comment = :idBeerReviewComment;
