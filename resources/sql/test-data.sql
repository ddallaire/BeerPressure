DELETE FROM brewery_review_user_thumbsup;
DELETE FROM brewery_review_comment;
DELETE FROM brewery_review;
DELETE FROM beer_review_user_thumbsup;
DELETE FROM beer_review_comment;
DELETE FROM beer_review;
DELETE FROM brewery_user_rating;
DELETE FROM beer_user_rating;
DELETE FROM beer_user_drank;
DELETE FROM beer_tag;
DELETE FROM tag_brewery;
DELETE FROM tag;
DELETE FROM beer_brewery;
DELETE FROM beer;
DELETE FROM brewery;
DELETE FROM user_follow;
DELETE FROM "user";

INSERT INTO "user"(cip, name, surname) VALUES
    ('alig2503', 'Gaétan', 'Allano'),
    ('dald2202', 'David', 'Dallaire'),
    ('lara2318', 'Andy', 'Larochelle'),
    ('mahm1904', 'Marc-Antoine', 'Maheux'),
    ('parp2009', 'Pierre', 'Parrat'),
    ('pele1704', 'Étienne', 'Pelletier'),
    ('royj1933', 'Jérémie', 'Roy');

INSERT INTO user_follow(follower_cip, followee_cip) VALUES
    ('alig2503', 'lara2318'),
    ('dald2202', 'lara2318'),
    ('lara2318', 'pele1704'),
    ('mahm1904', 'lara2318'),
    ('parp2009', 'lara2318'),
    ('pele1704', 'lara2318'),
    ('royj1933', 'lara2318');

INSERT INTO brewery(id_brewery, name, description, image_path) VALUES
    (1, 'MolsonCoors', 'Ils ne font de la marde comme bière.', 'https://images.radio-canada.ca/q_auto,w_1250/v1/ici-info/16x9/molson-montreal-notre-dame.jpg'),
    (2, 'Labatt', 'Une copie de MolsonCoors en pire...', 'https://upload.wikimedia.org/wikipedia/fr/4/40/Labatt.png'),
    (3, 'Unibroue', 'La bière préférée des gars en génie qui ne connaissent pas ce qui est de la vrai bière.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeNRjlWTRpm31yyz_mHoGAE9UdjNaAnt3WVCd9E_FsBnlNnLcGcw'),
    (4, 'Dieu du Ciel!', 'Enfin!! Une brasserie qui fait de la bonne bière.', 'http://theatregillesvigneault.com/wp-content/uploads/2017/03/DIEU-DU-CIEL.jpg'),
    (5, 'Siboire', 'Une brasserie de Sherbrooke.', 'http://www.ambq.ca/mod/file/MembreFile/c45147dee729311ef5b5c3003946c48f.png'),
    (6, 'SherBroue', 'Génie Sherbrooke!!!', 'http://sherbroue.ca/accueil/wp-content/uploads/2012/02/Couverture-Facebook-1.jpg');

INSERT INTO beer(id_beer, name, description, ibu, alcohol_percent, image_path) VALUES
    (1, 'Molson Canadian', 'Une bière avec une feuille d''érable dessus...', 15, 5.0, 'https://decrescente.net/images/suppliers/millercoors/molson/molson-canadian/canadian-bottle-lg.png'),
    (2, 'Blue', 'Une bière bleu... Étrange', 12, 5.0, 'https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwir8pfW-7DbAhWr5oMKHX3oDtMQjRx6BAgBEAU&url=https%3A%2F%2Fwww.liquormarts.ca%2Fproduct%2Flabatt-blue%2F24-x-355-ml&psig=AOvVaw1n1N32iTLqUZu3QGlvQy7F&ust=1527891003844616'),
    (3, 'Don de Dieu', 'Une bière pas bonne qui souale...', 10, 9.0, 'https://res.cloudinary.com/ratebeer/image/upload/d_beerdefault_m1aurr.png,f_auto/beer_1929'),
    (4, 'Péché Mortel', 'La seule bonne bière du site.', 76, 9.5, 'http://images.lpcdn.ca/641x427/201703/31/1377153.jpg'),
    (5, 'Ingénieuse', 'Bière collaborative.', 17, 5.1, 'https://cdn.narcity.com/uploads/249500_90d9f57c70a3e15b6e0a08e67dcf1d944038212a.jpg');

INSERT INTO beer_brewery(id_brewery, id_beer) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 5);

INSERT INTO tag (id_tag, name) VALUES
    (1, 'Industrielle'),
    (2, 'Artisanale'),
    (3, 'Bon choix'),
    (4, 'Mauvais choix');

INSERT INTO tag_brewery(id_brewery, id_tag) VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 2),
    (4, 3),
    (5, 2),
    (6, 2);

INSERT INTO beer_tag(id_beer, id_tag) VALUES
    (1, 1),
    (1, 4),
    (2, 1),
    (2, 4),
    (3, 2),
    (3, 4),
    (4, 2),
    (4, 3),
    (5, 2),
    (5, 4);

INSERT INTO beer_user_drank(cip, id_beer) VALUES
    ('alig2503', 3),
    ('alig2503', 4),
    ('dald2202', 3),
    ('dald2202', 4),
    ('dald2202', 5),
    ('lara2318', 4),
    ('mahm1904', 3),
    ('mahm1904', 5),
    ('parp2009', 3),
    ('parp2009', 4),
    ('pele1704', 3),
    ('pele1704', 4),
    ('royj1933', 3),
    ('royj1933', 4);

INSERT INTO beer_user_rating(cip, id_beer, rating) VALUES
    ('alig2503', 3, 2.5),
    ('alig2503', 4, 3.5),
    ('dald2202', 3, 2.5),
    ('dald2202', 4, 5.0),
    ('dald2202', 5, 3.5),
    ('lara2318', 4, 5.0),
    ('mahm1904', 3, 2.0),
    ('mahm1904', 5, 4.0),
    ('parp2009', 3, 2.5),
    ('parp2009', 4, 3.5),
    ('pele1704', 3, 2.0),
    ('pele1704', 4, 5.0),
    ('royj1933', 3, 3.0),
    ('royj1933', 4, 5.0);

INSERT INTO brewery_user_rating(cip, id_brewery, rating) VALUES
    ('alig2503', 1, 1.0),
    ('dald2202', 1, 2.0),
    ('lara2318', 2, 0.5),
    ('mahm1904', 2, 1.5),
    ('parp2009', 3, 2.5),
    ('pele1704', 3, 1.5),
    ('royj1933', 3, 1.0);

INSERT INTO beer_review(id_beer_review, cip, id_beer, title, content, image_path, rating, time) VALUES
    (1, 'alig2503', 1, 'Une mauvaise fierté canadienne', 'Le titre était mon contenu... Il n''a vraiment rien à dire.', NULL, 1.0, TIMESTAMP '2018-06-10 10:00:00-4'),
    (2, 'dald2202', 3, 'Beaucoup d''alcool!', 'C''est parfait pour être saoul!!!', 'http://benpourquoipas.com/wp-content/uploads/2012/10/bebe-saoul-marcher-sobre.jpg', 2.5, TIMESTAMP '2018-06-10 10:00:01-4'),
    (3, 'lara2318', 4, 'Que c''est bon!!', 'C''est pas pire pour une soirée entre boux!!', NULL, 4.5, TIMESTAMP '2018-06-10 10:00:02-4'),
    (4, 'mahm1904', 5, 'Je n''aime pas la bière, mais ça se boit!!', 'Ça se vend au Siboire... Je suis con c''est écrit dans la description.', NULL, 1.5, TIMESTAMP '2018-06-10 10:00:03-4'),
    (5, 'parp2009', 1, 'Un autre truc pas bon!', 'CE N''EST PAS BON...', NULL, 2.0, TIMESTAMP '2018-06-10 10:00:04-4'),
    (6, 'pele1704', 4, 'Tu as raison Andy!!', 'Damn... c''est un review, pas un commentaire...', 'https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjn1Pr_wLPbAhXKtlkKHX7EBJAQjRx6BAgBEAU&url=http%3A%2F%2Fknowyourmeme.com%2Fphotos%2F56807-fail-epic-fail&psig=AOvVaw3HfgoRpGtV9HRR5-0cLFJ0&ust=1527978321220516', 4.0, TIMESTAMP '2018-06-10 10:00:05-4'),
    (7, 'royj1933', 4, 'Époustouflant!!!', 'Je n''ai pas d''autre', NULL, 5.0, TIMESTAMP '2018-06-10 10:00:06-4');

INSERT INTO beer_review_comment(id_beer_review_comment, id_beer_review, cip, content, time) VALUES
    (1, 1, 'pele1704', 'Je suis d''accord avec toi!', TIMESTAMP '2018-06-10 10:00:10-4'),
    (2, 2, 'parp2009', 'J''ai tellement été saoul un soir en buvant ça!!!', TIMESTAMP '2018-06-10 10:00:11-4'),
    (3, 3, 'mahm1904', 'Peut-être que j''aimerais ça!', TIMESTAMP '2018-06-10 10:00:12-4'),
    (4, 4, 'lara2318', 'C''est de la marde cette bière... Tu dis n''importe quoi...', TIMESTAMP '2018-06-10 10:00:13-4'),
    (5, 5, 'dald2202', 'Tu as bien raison!!', TIMESTAMP '2018-06-10 10:00:14-4'),
    (6, 3, 'alig2503', 'C''est vraiment bon ça!!!', TIMESTAMP '2018-06-10 10:00:15-4'),
    (7, 3, 'royj1933', 'C''est nice les soirée entre boux! Je fais de la crème brulée', TIMESTAMP '2018-06-10 10:00:16-4');

INSERT INTO beer_review_user_thumbsup(id_beer_review, cip) VALUES
    (1, 'dald2202'),
    (1, 'lara2318'),
    (1, 'pele1704'),
    (3, 'dald2202'),
    (4, 'pele1704'),
    (5, 'mahm1904'),
    (6, 'alig2503'),
    (7, 'parp2009'),
    (2, 'royj1933');

INSERT INTO brewery_review(id_brewery_review, cip, id_brewery, title, content, image_path, rating, time) VALUES
    (1, 'alig2503', 1, 'Une mauvaise brasserie montréalaise...', 'Sans autre commentaire...', NULL, 1.5, TIMESTAMP '2018-06-10 10:00:00-4'),
    (2, 'dald2202', 2, 'Le brasseur de la 50...', 'Ce n''est pas grand exploit...', 'https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiylLDdxrPbAhXJjVkKHdQ0AfUQjRx6BAgBEAU&url=http%3A%2F%2Fwww.labatt.com%2Fbrands%2Fnationalbrands.php%3Flanguage%3Dfr&psig=AOvVaw3AY0gWiexTaG1xbjUkwtVH&ust=1527979870192306', 1.5, TIMESTAMP '2018-06-10 10:00:01-4'),
    (3, 'lara2318', 3, 'Ordinaire...', 'Toutes leurs bières ont le même goût...', NULL, 0.1, TIMESTAMP '2018-06-10 10:00:02-4'),
    (4, 'mahm1904', 6, 'Encouragement!!!', 'Vive génie sherbrooke!!!', NULL, 4.0, TIMESTAMP '2018-06-10 10:00:03-4'),
    (5, 'parp2009', 5, 'Bonne bouffe!!', 'Le Mac&Cheese est super.', NULL, 5.0, TIMESTAMP '2018-06-10 10:00:04-4'),
    (6, 'pele1704', 4, 'Bravo!', 'Ils font de très bonnes bières!!', NULL, 4.5, TIMESTAMP '2018-06-10 10:00:05-4'),
    (7, 'royj1933', 5, 'Super bouffe!!!', 'Bravo au chef!', NULL, 4.0, TIMESTAMP '2018-06-10 10:00:06-4');

INSERT INTO brewery_review_comment(id_brewery_review_comment, id_brewery_review, cip, content, time) VALUES
    (1, 2, 'alig2503', 'Comment oses-tu dire ceci?', TIMESTAMP '2018-06-10 10:00:10-4'),
    (2, 3, 'dald2202', 'Je suis bien d''accord!', TIMESTAMP '2018-06-10 10:00:11-4'),
    (3, 4, 'lara2318', 'Tu ne connais rien à la bière...', TIMESTAMP '2018-06-10 10:00:12-4'),
    (4, 5, 'mahm1904', 'Party!!!', TIMESTAMP '2018-06-10 10:00:13-4'),
    (5, 5, 'parp2009', 'PARTY!!! PARTY!!! PARTY!!! PARTY!!!',  TIMESTAMP '2018-06-10 10:00:14-4'),
    (6, 7, 'pele1704', 'On y va quand?', TIMESTAMP '2018-06-10 10:00:15-4'),
    (7, 1, 'royj1933', 'Parce que c''est vrai...', TIMESTAMP '2018-06-10 10:00:16-4');

INSERT INTO brewery_review_user_thumbsup(id_brewery_review, cip) VALUES
    (1, 'dald2202'),
    (1, 'lara2318'),
    (1, 'pele1704'),
    (3, 'dald2202'),
    (4, 'pele1704'),
    (5, 'mahm1904'),
    (6, 'alig2503'),
    (7, 'parp2009'),
    (2, 'royj1933');

