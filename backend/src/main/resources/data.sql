insert into muscle_group (name, description) values('Back', 'The back muscles, including the lats, trapezius, and erector spinae, are essential for posture, pulling, and lifting. A well-developed back not only enhances your physique but also supports overall strength and stability.');
insert into muscle_group (name, description) values('Shoulders', 'The shoulder muscles, or deltoids, provide the upper body with a balanced, rounded appearance. They are involved in various arm and shoulder movements and contribute to an aesthetically pleasing physique.');
insert into muscle_group (name, description) values('Abdominals', 'The abdominal muscles, including the rectus abdominis and obliques, are essential for core stability and contribute to a lean and sculpted midsection. A strong core is vital for overall strength and posture.');
insert into muscle_group (name, description) values('Legs', 'The leg muscles consist of the quadriceps (front thighs) and hamstrings (back thighs). Strong legs are essential for functional strength and athletic performance. Leg exercises like squats and deadlifts are fundamental in bodybuilding.');
insert into muscle_group (name, description) values('Triceps', 'The triceps, situated on the back of the upper arm, are responsible for straightening the arm at the elbow and play a pivotal role in various pushing movements and overall arm strength.');
insert into muscle_group (name, description) values('Biceps', 'The biceps, located on the front of the upper arm, are responsible for bending the arm at the elbow, contributing to both strength and aesthetics.');
insert into muscle_group (name, description) values('Chest', 'The chest muscles, or pectoralis group, consist of the pectoralis major and minor. The major muscle provides bulk to the chest, facilitating arm movements, while the minor muscle aids in shoulder stability.');

insert into exercise (name, description) values('Squat', 'A fundamental compound exercise that targets the muscles in the lower body, primarily the quadriceps, hamstrings, glutes, and lower back.');
insert into exercise (name, description) values('Deadlift', 'A powerful and compound strength training exercise that primarily targets the muscles in the lower body and lower back, also known for its ability to develop overall strength and functional fitness.');
insert into exercise (name, description) values('Military press', 'A compound strength training exercise that primarily targets the muscles of the shoulders and triceps, building shoulder stability.');
insert into exercise (name, description) values('Close grip bench press', 'A variation of the traditional bench press that targets the triceps and chest muscles while reducing the involvement of the shoulders, enhancing tricep development.');
insert into exercise (name, description) values('Crunches', 'A popular abdominal exercise that specifically targets the muscles in the front the core, primarily the rectus abdominis.');
insert into exercise (name, description) values('Barbell curl', 'A classic and effective strength training exercise for developing the muscles of the upper arm, primarily focusing on the biceps.');

insert into exercise_muscle_group (exercise, muscle_group, name) values(1, 4, 'Legs');

insert into exercise_muscle_group (exercise, muscle_group, name) values(2, 1, 'Back');
insert into exercise_muscle_group (exercise, muscle_group, name) values(2, 4, 'Legs');

insert into exercise_muscle_group (exercise, muscle_group, name) values(3, 2, 'Shoulders');
insert into exercise_muscle_group (exercise, muscle_group, name) values(3, 5, 'Triceps');

insert into exercise_muscle_group (exercise, muscle_group, name) values(4, 5, 'Triceps');
insert into exercise_muscle_group (exercise, muscle_group, name) values(4, 7, 'Chest');

insert into exercise_muscle_group (exercise, muscle_group, name) values(5, 3, 'Abdominals');

insert into exercise_muscle_group (exercise, muscle_group, name) values(6, 6, 'Biceps');