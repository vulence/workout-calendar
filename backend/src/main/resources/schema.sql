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
    description text,
    exercise_count INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS exercise_muscle_group (
    exercise INTEGER,
    muscle_group INTEGER,
    name text,
    PRIMARY KEY (exercise, muscle_group),
    FOREIGN KEY (exercise) REFERENCES exercise(id) ON DELETE CASCADE,
    FOREIGN KEY (muscle_group) REFERENCES muscle_group(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION increment_exercise_count_function()
RETURNS TRIGGER AS '
BEGIN
    IF (NEW.muscle_group IS NOT NULL) THEN
        UPDATE muscle_group
        SET exercise_count = exercise_count + 1
        WHERE id = NEW.muscle_group;
        RETURN NEW;
    END IF;
END;
' LANGUAGE plpgsql;

CREATE TRIGGER increment_exercise_count
AFTER INSERT ON exercise_muscle_group
FOR EACH ROW
EXECUTE FUNCTION increment_exercise_count_function();