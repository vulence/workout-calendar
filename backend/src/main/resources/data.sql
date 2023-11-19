INSERT INTO MUSCLE_GROUP (name, description) VALUES('Back', 'The back muscles, including the lats, trapezius, and erector spinae, are essential for posture, pulling, and lifting. A well-developed back not only enhances your physique but also supports overall strength and stability.');
INSERT INTO MUSCLE_GROUP (name, description) VALUES('Shoulders', 'The shoulder muscles, or deltoids, provide the upper body with a balanced, rounded appearance. They are involved in various arm and shoulder movements and contribute to an aesthetically pleasing physique.');
INSERT INTO MUSCLE_GROUP (name, description) VALUES('Abdominals', 'The abdominal muscles, including the rectus abdominis and obliques, are essential for core stability and contribute to a lean and sculpted midsection. A strong core is vital for overall strength and posture.');
INSERT INTO MUSCLE_GROUP (name, description) VALUES('Legs', 'The leg muscles consist of the quadriceps (front thighs) and hamstrings (back thighs). Strong legs are essential for functional strength and athletic performance. Leg exercises like squats and deadlifts are fundamental in bodybuilding.');
INSERT INTO MUSCLE_GROUP (name, description) VALUES('Triceps', 'The triceps, situated on the back of the upper arm, are responsible for straightening the arm at the elbow and play a pivotal role in various pushing movements and overall arm strength.');
INSERT INTO MUSCLE_GROUP (name, description) VALUES('Biceps', 'The biceps, located on the front of the upper arm, are responsible for bending the arm at the elbow, contributing to both strength and aesthetics.');

INSERT INTO EXERCISE (name, description) VALUES('Squat', 'A fundamental compound exercise that targets the muscles in the lower body, primarily the quadriceps, hamstrings, glutes, and lower back.');
INSERT INTO EXERCISE (name, description) VALUES('Deadlift', 'A powerful and compound strength training exercise that primarily targets the muscles in the lower body and lower back, also known for its ability to develop overall strength and functional fitness.');
INSERT INTO EXERCISE (name, description) VALUES('Military press', 'A compound strength training exercise that primarily targets the muscles of the shoulders and triceps, building shoulder stability.');
INSERT INTO EXERCISE (name, description) VALUES('Close grip bench press', 'A variation of the traditional bench press that targets the triceps and chest muscles while reducing the involvement of the shoulders, enhancing tricep development.');
INSERT INTO EXERCISE (name, description) VALUES('Crunches', 'A popular abdominal exercise that specifically targets the muscles in the front the core, primarily the rectus abdominis.');
INSERT INTO EXERCISE (name, description) VALUES('Barbell curl', 'A classic and effective strength training exercise for developing the muscles of the upper arm, primarily focusing on the biceps.');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(1, 4, 'Legs');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(2, 1, 'Back');
INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(2, 4, 'Legs');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(3, 2, 'Shoulders');
INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(3, 5, 'Triceps');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(4, 5, 'Triceps');
INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(4, 7, 'Chest');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(5, 3, 'Abdominals');

INSERT INTO EXERCISE_MUSCLE_GROUP (exercise, muscle_group, name) VALUES(6, 6, 'Biceps');