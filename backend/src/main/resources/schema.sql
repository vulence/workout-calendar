DROP TABLE IF EXISTS workout_exercise;
DROP TABLE IF EXISTS exercise_muscle_group;
DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS muscle_group;

CREATE TABLE IF NOT EXISTS workout (
    id SERIAL PRIMARY KEY,
    title text,
    date TIMESTAMP,
    duration INTEGER,
    notes text,
    rating INTEGER,
    user_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise (
    id SERIAL PRIMARY KEY,
    name text,
    description text,
    image_url text
);

CREATE TABLE IF NOT EXISTS workout_exercise (
    id SERIAL PRIMARY KEY,
    workout_id INTEGER NOT NULL,
    weight INTEGER,
    sets INTEGER,
    reps INTEGER,
    exercise_id INTEGER NOT NULL,
    completed BOOLEAN,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (workout_id) REFERENCES workout(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS muscle_group (
    id SERIAL PRIMARY KEY,
    name text,
    description text
);

CREATE TABLE IF NOT EXISTS exercise_muscle_group (
    id SERIAL PRIMARY KEY,
    "primary" BOOLEAN,
    exercise_id INTEGER NOT NULL,
    muscle_group_id INTEGER NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id) ON DELETE CASCADE
);