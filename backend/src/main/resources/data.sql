insert into muscle_group (name, description) values('Back', 'The back muscles, including the lats, trapezius, and erector spinae, are essential for posture, pulling, and lifting. A well-developed back not only enhances your physique but also supports overall strength and stability.');
insert into muscle_group (name, description) values('Shoulders', 'The shoulder muscles, or deltoids, provide the upper body with a balanced, rounded appearance. They are involved in various arm and shoulder movements and contribute to an aesthetically pleasing physique.');
insert into muscle_group (name, description) values('Abdominals', 'The abdominal muscles, including the rectus abdominis and obliques, are essential for core stability and contribute to a lean and sculpted midsection. A strong core is vital for overall strength and posture.');
insert into muscle_group (name, description) values('Legs', 'The leg muscles consist of the quadriceps (front thighs) and hamstrings (back thighs). Strong legs are essential for functional strength and athletic performance. Leg exercises like squats and deadlifts are fundamental in bodybuilding.');
insert into muscle_group (name, description) values('Triceps', 'The triceps, situated on the back of the upper arm, are responsible for straightening the arm at the elbow and play a pivotal role in various pushing movements and overall arm strength.');
insert into muscle_group (name, description) values('Biceps', 'The biceps, located on the front of the upper arm, are responsible for bending the arm at the elbow, contributing to both strength and aesthetics.');
insert into muscle_group (name, description) values('Chest', 'The chest muscles, or pectoralis group, consist of the pectoralis major and minor. The major muscle provides bulk to the chest, facilitating arm movements, while the minor muscle aids in shoulder stability.');

insert into exercise (name, description, image_url) values('Squat', 'A fundamental compound exercise that targets the muscles in the lower body, primarily the quadriceps, hamstrings, glutes, and lower back.', 'https://149874912.v2.pressablecdn.com/wp-content/uploads/2021/05/Squat-depth.jpg');
insert into exercise (name, description, image_url) values('Deadlift', 'A powerful and compound strength training exercise that primarily targets the muscles in the lower body and lower back, also known for its ability to develop overall strength and functional fitness.', 'https://images.ctfassets.net/8urtyqugdt2l/dSUzhyCmJfSjz5ZZyeCdo/72c87d62fb44c013943440c98cbfd080/sumo-vs-conventional-deadlift.jpg');
insert into exercise (name, description, image_url) values('Military press', 'A compound strength training exercise that primarily targets the muscles of the shoulders and triceps, building shoulder stability.', 'https://www.bodybuilding.com/images/2017/may/6-tips-for-improving-your-overhead-press-header-v2-960x540.jpg');
insert into exercise (name, description, image_url) values('Close grip bench press', 'A variation of the traditional bench press that targets the triceps and chest muscles while reducing the involvement of the shoulders, enhancing tricep development.', 'https://barbend.com/wp-content/uploads/2019/05/Barbend-Featured-Image-1600x900-A-person-doing-a-close-grip-barbell-bench-press-1.jpg');
insert into exercise (name, description, image_url) values('Crunches', 'A popular abdominal exercise that specifically targets the muscles in the front the core, primarily the rectus abdominis.', 'https://img.freepik.com/premium-photo/high-angle-side-view-determined-adult-bearded-male-sportswear-doing-crunches-while-training-abdominal-muscles-during-intense-workout-gym_251859-5515.jpg');
insert into exercise (name, description, image_url) values('Barbell curl', 'A classic and effective strength training exercise for developing the muscles of the upper arm, primarily focusing on the biceps.', 'https://c4.wallpaperflare.com/wallpaper/855/739/772/arnold-schwarzenegger-barbell-bodybuilder-bodybuilding-wallpaper-thumb.jpg');
insert into exercise (name, description, Image_url) values('Bench press', 'Bench press is a fundamental weightlifting exercise performed on a flat bench with a barbell or dumbbells. It targets the muscles of the chest, shoulders, and triceps, making it a key exercise for developing upper body strength and muscle mass.'. 'https://t4.ftcdn.net/jpg/00/95/32/41/360_F_95324105_nanCVHMiu7r8B0qSur3k1siBWxacfmII.jpg')
insert into exercise (name, description, Image_url) values('Incline bench press', 'The incline bench press is a weightlifting exercise that targets the upper chest, shoulders, and triceps. It involves lying on an inclined bench and pushing a barbell or dumbbells upward from the upper chest to full arm extension, helping to develop strength and muscle definition in the upper body.', 'https://i0.wp.com/www.muscleandfitness.com/wp-content/uploads/2019/12/Incline-Barbell-Bench-Press.jpg?quality=86&strip=all')

insert into exercise_muscle_group (exercise, muscle_group, name) values(1, 4, 'Legs');

insert into exercise_muscle_group (exercise, muscle_group, name) values(2, 1, 'Back');
insert into exercise_muscle_group (exercise, muscle_group, name) values(2, 4, 'Legs');

insert into exercise_muscle_group (exercise, muscle_group, name) values(3, 2, 'Shoulders');
insert into exercise_muscle_group (exercise, muscle_group, name) values(3, 5, 'Triceps');

insert into exercise_muscle_group (exercise, muscle_group, name) values(4, 5, 'Triceps');
insert into exercise_muscle_group (exercise, muscle_group, name) values(4, 7, 'Chest');

insert into exercise_muscle_group (exercise, muscle_group, name) values(5, 3, 'Abdominals');

insert into exercise_muscle_group (exercise, muscle_group, name) values(6, 6, 'Biceps');

insert into exercise_muscle_group (exercise, muscle_group, name) values(7, 7, 'Chest');
insert into exercise_muscle_group (exercise, muscle_group, name) values(7, 5, 'Triceps');

insert into exercise_muscle_group (exercise, muscle_group, name) values(8, 7, 'Chest');
insert into exercise_muscle_group (exercise, muscle_group, name) values(8, 5, 'Triceps');