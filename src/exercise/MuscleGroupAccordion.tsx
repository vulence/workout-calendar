import { Accordion, AccordionSummary, AccordionDetails, Typography, ListItemContent } from "@mui/joy";
import { MuscleGroup } from "../types";

interface MuscleGroupAccordionProps {
    muscleGroup: MuscleGroup,
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
            <AccordionDetails sx={{borderRadius: "20px"}}>
                <Typography sx={{ color: "white" }}>Ovde ce da budu sve vezbe za tu misicnu grupu.</Typography>
            </AccordionDetails>
        </Accordion>
    );
}