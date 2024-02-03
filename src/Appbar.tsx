import * as React from 'react';
import { useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import SwipeableDrawer from '@mui/material/SwipeableDrawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import LogoutIcon from '@mui/icons-material/Logout';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import SportsGymnasticsIcon from '@mui/icons-material/SportsGymnastics';
import { Link } from "react-router-dom";

import { AuthContext } from './auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType, IconMap } from './types';
import { Button } from '@mui/material';

const pages = ['Workouts', 'Exercises', 'Muscle Groups'];
const settings = ['Workouts', 'Exercises', 'Account', 'Logout'];

function ResponsiveAppBar() {
  // Loads in the authentication status and user object from context
  const { authenticated, user } = useContext<AuthContextType>(AuthContext);

  const [anchorElUser, setAnchorElUser] = React.useState<HTMLElement | null>(null);
  const [state, setState] = useState<boolean>(false);

  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement> | React.KeyboardEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  // Opens or closes the left-side drawer
  const toggleDrawer = (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
    if (
      event &&
      'type' in event &&
      (event.type === 'keydown' && (event as React.KeyboardEvent).key === 'Tab' || (event as React.KeyboardEvent).key === 'Shift')
    ) {
      return;
    }

    setState(open);
  };

  // Loads in all the pages for the left-side drawer
  const list = () => (
    <Box
      sx={{ width: 250 }}
      role="presentation"
      onClick={toggleDrawer(false)}
      onKeyDown={toggleDrawer(false)}
    >
      <List>
        {pages.map((page) => (
          <Link key={page} to={page.replace(/\s/g, '').toLowerCase()} style={{ color: 'inherit', textDecoration: 'none' }}>
            <ListItem key={page} disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <FitnessCenterIcon sx={{ color: "white" }} />
                </ListItemIcon>
                <ListItemText primary={page} />
              </ListItemButton>
            </ListItem>
          </Link>
        ))}
      </List>
    </Box>
  );

  const iconMap: IconMap = {
    workouts: <FitnessCenterIcon fontSize="small" />,
    exercises: <SportsGymnasticsIcon fontSize="small" />,
    account: <AccountCircleIcon fontSize="small" />,
    logout: <LogoutIcon fontSize="small" />
  };

  return (
    <AppBar position="sticky">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          {authenticated ? (
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
              onClick={toggleDrawer(true)}
            >
              <MenuIcon />
            </IconButton>
          ) : null}

          <SwipeableDrawer
            open={state}
            onClose={toggleDrawer(false)}
            onOpen={toggleDrawer(true)}
            PaperProps={{
              sx: {
                backgroundColor: "black",
                color: "white",
              }
            }}
          >
            {list()}
          </SwipeableDrawer>

          <IconButton component={Link} to="/home" sx={{ marginLeft: "auto" }}>
            <FitnessCenterIcon sx={{ marginLeft: "auto", display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          </IconButton>

          <Typography
            variant="h6"
            noWrap
            component={Link}
            to="/home"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'Caveat',
              fontWeight: 700,
              fontSize: 25,
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            Rise & Grind
          </Typography>

          <IconButton component={Link} to="/home">
            <FitnessCenterIcon sx={{ marginLeft: "auto", display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          </IconButton>

          <Box sx={{ marginLeft: "auto", flexGrow: 0, position: 'relative', zIndex: 1 }}>
            {authenticated ? (
              <>
                <Tooltip title="Open settings">
                  <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                    <Avatar>{user?.username.charAt(0).toUpperCase()}</Avatar>
                  </IconButton>
                </Tooltip>
                <Menu
                  sx={{ mt: '45px' }}
                  id="menu-appbar"
                  anchorEl={anchorElUser}
                  anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  open={Boolean(anchorElUser)}
                  onClose={handleCloseUserMenu}
                >
                  {settings.map((setting) => (
                    <Link key={setting} to={setting.replace(/\s/g, '').toLowerCase()} style={{ textDecoration: "none", color: "white" }}>
                      <MenuItem key={setting} onClick={handleCloseUserMenu}>
                        <ListItemIcon>
                          {iconMap[setting.replace(/\s/g, '').toLowerCase()]}
                        </ListItemIcon>
                        <Typography textAlign="center">{setting}</Typography>
                      </MenuItem>
                    </Link>
                  ))}
                </Menu>
              </>
            ) : null}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;