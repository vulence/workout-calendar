DROP TABLE IF EXISTS workout_exercise;
DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS muscle_group;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS completed_workout;
DROP TABLE IF EXISTS exercise_set;

CREATE TABLE IF NOT EXISTS workout (
    id SERIAL PRIMARY KEY,
    title text,
    duration INTEGER,
    notes text,
    rating INTEGER,
    user_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise (
    id SERIAL PRIMARY KEY,
    name text,
    description text,
    image_url text,
    FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS workout_exercise (
    id SERIAL PRIMARY KEY,
    workout_id INTEGER NOT NULL,
    exercise_id INTEGER NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (workout_id) REFERENCES workout(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    userfront_id UUID NOT NULL UNIQUE,
    email text,
    join_date TIMESTAMP,
    username text
);

CREATE TABLE IF NOT EXISTS completed_workout (
    id SERIAL PRIMARY KEY,
    workout_id INTEGER NOT NULL,
    date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise_set (
    id SERIAL PRIMARY KEY,
    workout_exercise_id INTEGER NOT NULL,
    set_type text,
    weight INTEGER,
    reps INTEGER
);