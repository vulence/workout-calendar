
import { Box, Container, Typography, Card, CardContent, ButtonGroup, Button, Divider } from '@mui/material';

import styles from './account.module.css';

export default function Account() {

    return (
        <Container className={styles.content}>
            <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "row" }}>
                <Box sx={{flex: 1, padding: 2, borderRadius: 5, display: "flex", alignItems: "center", justifyContent: "center", border: "1px solid #575653"}}>
                    <Button variant="contained" sx={{marginRight: 5, backgroundColor: "grey"}}>GENERAL</Button>
                    <Button variant="contained" sx={{marginLeft: 5, backgroundColor: "grey"}}>PRIVACY</Button>
                </Box>

                <Card variant="outlined" sx={{ width: "50%", margin: 3, borderRadius: 5, flex: 1 }}>
                    <CardContent sx={{ display: "flex", flexDirection: "row" }}>
                        <img src="workhard.jpg" width="20%" height={100} />
                        <Box sx={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", width: "80%"}}>
                            <Typography>Hello, Vukasin</Typography>
                            <Typography color="grey">vukasinmarinkovic0@gmail.com</Typography>
                        </Box>
                    </CardContent>
                </Card>

                <Box sx={{flex: 1, padding: 2, borderRadius: 5, display: "flex", alignItems: "center", justifyContent: "center", border: "1px solid #575653"}}>
                    <Button variant="contained" sx={{marginRight: 5, backgroundColor: "grey"}}>SECURITY</Button>
                    <Button variant="contained" sx={{marginLeft: 5, backgroundColor: "grey"}}>ADDITIONAL</Button>
                </Box>
            </Box>
        </Container>
    );
}