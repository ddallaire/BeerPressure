-- name: get-brewery-review-comment
SELECT id_brewery_review_comment, "user".cip, name, surname, id_brewery_review, content, time FROM brewery_review_comment
    INNER JOIN "user" ON "user".cip = brewery_review_comment.cip
    WHERE id_brewery_review_comment = :idBreweryReviewComment;
