DO
$$
    DECLARE
        scriptName VARCHAR := 'Insert person and post data';
    BEGIN
        RAISE
            NOTICE 'Start of % ...', scriptName;

        -- Insert Users
        INSERT INTO person (login)
        VALUES ('JohnDoe'),
               ('AliceSmith'),
               ('BobJohnson'),
               ('EmilyBrown'),
               ('MichaelLee'),
               ('SaraMiller'),
               ('DavidClark'),
               ('OliviaJones'),
               ('WilliamMoore'),
               ('EmmaWilson');

-- Insert Posts
        INSERT INTO post (title, body, author_id)
        VALUES ('Exploring the Mountains',
                'Just came back from a wonderful hiking trip in the mountains!', 1),
               ('Cooking Adventures', 'Tried a new recipe today, and it turned out amazing!', 2),
               ('Book Recommendations',
                'Can anyone suggest a good book to read? Looking for recommendations.', 3),
               ('Travel Diary', 'Visited a beautiful city and enjoyed every moment of it.', 4),
               ('Tech Talk', 'Exciting developments in the tech world. Let''s discuss!', 5),
               ('Fitness Journey', 'Started a new fitness routine. Feeling motivated!', 6),
               ('Movie Night', 'Any movie recommendations for a cozy night in?', 7),
               ('Art and Creativity',
                'Explored my artistic side today. Here''s my latest creation!', 8),
               ('Coding Challenges',
                'Solved a challenging coding problem. Celebrating small victories!', 9),
               ('Gardening Delights',
                'Spent the day in the garden. Nature has its own way of calming the soul.', 10),
               ('Favorite Recipes', 'Sharing a delicious recipe that never fails to impress.', 1),
               ('Mindfulness Moments', 'Taking a moment to appreciate the simple joys of life.', 2),
               ('Music Discoveries', 'Discovered some new music that I can''t stop listening to.',
                3),
               ('Pets and Companions', 'Introducing my furry friend to the world. Meet Fluffy!', 4),
               ('Work-Life Balance', 'Finding the right balance between work and personal life.',
                5);

-- Insert Likes
        INSERT INTO post_likes (post_id, person_id)
        VALUES (1, 2),
               (2, 3),
               (3, 4), (3, 5),
               (4, 5), (4, 6),
               (5, 6), (5, 7),
               (6, 7), (6, 8),
               (7, 8), (7, 9),
               (8, 9), (8, 10),
               (9, 10), (9, 1),
               (10, 1), (10, 2),
               (11, 1), (11, 2),
               (12, 1), (12, 2),
               (13, 1), (13, 2),
               (14, 1), (14, 2), (14, 3);

-- Insert Subscriptions
        INSERT INTO person_subscriptions (person_id, subscription_id)
        VALUES (1, 2), (1, 3), (1, 4),
               (2, 3), (2, 1),
               (3, 4), (3, 1),
               (4, 5),
               (5, 6), (5, 1), (5, 2), (5, 3),
               (6, 7),
               (9, 10);

    END
$$
