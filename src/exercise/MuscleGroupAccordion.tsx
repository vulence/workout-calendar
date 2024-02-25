import { Accordion, AccordionSummary, AccordionDetails, Typography, ListItemContent, Grid } from "@mui/joy";
import { Exercise, MuscleGroup } from "../types";
import ExerciseCard from "./ExerciseCard";
import { ThreeDots } from "react-loader-spinner";

interface MuscleGroupAccordionProps {
    muscleGroup: MuscleGroup,
    exercises: Exercise[],
    handleClick: (muscleGroupId: number) => void
}

export default function MuscleGroupAccordion(props: MuscleGroupAccordionProps) {
    return (
        <Accordion
            sx={{
                marginBottom: "10px",
                borderRadius: "20px"
            }}
            onChange={(_, expanded) => expanded ? props.handleClick(props.muscleGroup.id) : null}
        >
            <AccordionSummary>
                <ListItemContent>
                    <Typography level="title-md" sx={{ color: "white" }}>{props.muscleGroup.name} ({props.muscleGroup.exerciseCount} {props.muscleGroup.exerciseCount === 1 ? "exercise" : "exercises"})</Typography>
                    <Typography level="body-sm" sx={{ color: "grey" }}>{props.muscleGroup.description}</Typography>
                </ListItemContent>
            </AccordionSummary>
            <AccordionDetails sx={{ borderRadius: "20px" }}>
                <Grid
                    container
                    rowSpacing={1}
                    columnSpacing={{ xs: 1, sm: 2, md: 3 }}
                    sx={{ width: "100%" }}
                >
                    {props.exercises ? (
                        props.exercises?.map((exercise) => (
                            <Grid key={exercise.id}>
                                <ExerciseCard exercise={exercise} />
                            </Grid>
                        ))) : (
                            <ThreeDots
                                visible={true}
                                height="80"
                                width="80"
                                color="white"
                                radius="9"
                                ariaLabel="three-dots-loading"
                                wrapperStyle={{
                                    marginLeft: "30px"
                                }}
                            />
                    )}
                </Grid>
            </AccordionDetails>
        </Accordion>
    );
}