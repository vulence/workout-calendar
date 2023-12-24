DROP TABLE IF EXISTS exercise_done;
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
    description text
);

CREATE TABLE IF NOT EXISTS exercise_done (
    id SERIAL PRIMARY KEY,
    workout INTEGER NOT NULL,
    weight INTEGER,
    sets INTEGER,
    reps INTEGER,
    exercise INTEGER,
    completed BOOLEAN,
    FOREIGN KEY (exercise) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (workout) REFERENCES workout(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS muscle_group (
    id SERIAL PRIMARY KEY,
    name text,
    description text
);

CREATE TABLE IF NOT EXISTS exercise_muscle_group (
    exercise INTEGER,
    muscle_group INTEGER,
    name text,
    PRIMARY KEY (exercise, muscle_group),
    FOREIGN KEY (exercise) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (muscle_group) REFERENCES muscle_group(id) ON DELETE CASCADE
);