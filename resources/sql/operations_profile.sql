-- name: get-brewery-reviews-written-by-query
SELECT id_brewery_review, "user".cip, name, surname, id_brewery, title, content, image_path, rating, time FROM brewery_review
    INNER JOIN "user" ON "user".cip = brewery_review.cip
    WHERE "user".cip = :cip
    ORDER BY time DESC
    LIMIT :breweryReviewedByFirst OFFSET :breweryReviewedBySkip;

-- name: get-beer-reviews-written-by-query
SELECT id_beer_review, "user".cip, name, surname, id_beer, title, content, image_path, rating, time FROM beer_review
    INNER JOIN "user" ON "user".cip = beer_review.cip
    WHERE "user".cip = :cip
    ORDER BY time DESC
    LIMIT :beerReviewedByFirst OFFSET :beerReviewedBySkip;

-- name: get-brewery-review-comments-query
SELECT id_brewery_review_comment, "user".cip, name, surname, id_brewery_review, content, time FROM brewery_review_comment
    INNER JOIN "user" ON "user".cip = brewery_review_comment.cip
    WHERE "user".cip = :cip
    ORDER BY time DESC
    LIMIT :breweryReviewsCommentedByFirst OFFSET :breweryReviewsCommentedBySkip;

-- name: get-beer-review-comments-query
SELECT id_beer_review_comment, id_beer_review, "user".cip, name, surname, content, time FROM beer_review_comment
    INNER JOIN "user" ON "user".cip = beer_review_comment.cip
    WHERE "user".cip = :cip
    ORDER BY time DESC
    LIMIT :beerReviewsCommentedByFirst OFFSET :beerReviewsCommentedBySkip;

-- name: get-brewery-reviews-thumbsup-by-query
SELECT brewery_review.id_brewery_review, "user".cip, name, surname, id_brewery, title, brewery_review.content, image_path, rating, brewery_review.time FROM brewery_review
    INNER JOIN "user" ON "user".cip = brewery_review.cip
    INNER JOIN brewery_review_user_thumbsup ON brewery_review_user_thumbsup.id_brewery_review = brewery_review.id_brewery_review
    WHERE brewery_review_user_thumbsup.cip = :cip
    GROUP BY brewery_review.id_brewery_review, "user".cip, name, surname, id_brewery, title, brewery_review.content, image_path, rating, brewery_review.time
    ORDER BY brewery_review.time DESC
    LIMIT :breweryReviewsThumbsupByFirst OFFSET :breweryReviewsThumbsupBySkip;

-- name: get-beer-reviews-thumbsup-by-query
SELECT beer_review.id_beer_review, "user".cip, name, surname, id_beer, title, beer_review.content, image_path, rating, beer_review.time FROM beer_review
    INNER JOIN "user" ON "user".cip = beer_review.cip
    INNER JOIN beer_review_user_thumbsup ON beer_review_user_thumbsup.id_beer_review = beer_review.id_beer_review
    WHERE beer_review_user_thumbsup.cip = :cip
    GROUP BY beer_review.id_beer_review, "user".cip, name, surname, id_beer, title, beer_review.content, image_path, rating, beer_review.time
    ORDER BY beer_review.time DESC
    LIMIT :beerReviewsThumbsupByFirst OFFSET :beerReviewsThumbsupBySkip;
