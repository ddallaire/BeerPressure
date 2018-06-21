-- name: get-brewery-review-comment
SELECT id_brewery_review_comment, "user".cip, name, surname, id_brewery_review, content, time FROM brewery_review_comment
    INNER JOIN "user" ON "user".cip = brewery_review_comment.cip
    WHERE id_brewery_review_comment = :idBreweryReviewComment;

-- name: insert-brewery-review-comment
INSERT INTO brewery_review_comment(cip, id_brewery_review, content, time) VALUES
    (:cip, :idBreweryReview, :content, now())
    RETURNING id_brewery_review_comment;

-- name: update-brewery-review-comment!
UPDATE brewery_review_comment SET content = :content
    WHERE id_brewery_review_comment = :idBreweryReviewComment AND cip = :cip;

-- name: delete-brewery-review-comment!
DELETE FROM brewery_review_comment WHERE id_brewery_review_comment = :id AND cip = :cip;
