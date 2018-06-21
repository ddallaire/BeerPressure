-- name: get-beer-review-comment
SELECT id_beer_review_comment, id_beer_review, "user".cip, name, surname, content, time FROM beer_review_comment
    INNER JOIN "user" ON "user".cip = beer_review_comment.cip
    WHERE id_beer_review_comment = :idBeerReviewComment;

-- name: insert-beer-review-comment
INSERT INTO beer_review_comment(cip, id_beer_review, content, time) VALUES
    (:cip, :idBeerReview, :content, now())
    RETURNING id_beer_review_comment;

-- name: update-beer-review-comment!
UPDATE beer_review_comment SET content = :content
    WHERE id_beer_review_comment = :idBeerReviewComment AND cip = :cip;

-- name: delete-beer-review-comment!
DELETE FROM beer_review_comment WHERE id_beer_review_comment = :id AND cip = :cip;
