import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ProductionQuantityLimitsIcon from '@mui/icons-material/ProductionQuantityLimits';
import HelpIcon from '@mui/icons-material/Help';
import { Routes, Route, Link } from 'react-router-dom';
// Assuming you have components for Users, Products, Requests
import Users from './Users';
import Products from './Products';
import Requests from './Requests';

const routes = [
  {
    name: 'Users',
    path: 'users',
    accessCodes: [0],
  },
  {
    name: 'Products',
    path: 'products',
    accessCodes: [0, 1],
  },
  {
    name: 'Requests',
    path: 'requests',
    accessCodes: [0],
  },
];

const drawerWidth = 240;

export default function Admin() {
  const userAccessRoute = 0;

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
      >
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            Dashboard
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar />
        <Divider />
        <List>
          {routes.map(({ name, path, accessCodes }, index) => {
            if (!accessCodes.includes(userAccessRoute)) return null;
            return (
              <ListItem key={name} disablePadding>
                <ListItemButton component={Link} to={path}>
                  <ListItemIcon>
                    {
                      path === "users" ? <AccountCircleIcon/>
                      : path === "products" ? <ProductionQuantityLimitsIcon/>
                      : path === "requests" ? <HelpIcon/>: <></>
                    }
                  </ListItemIcon>
                  <ListItemText primary={name} />
                </ListItemButton>
              </ListItem>
            );
          })}
        </List>
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}
      >
        <Toolbar />
        <Routes>
        <Route path="/" element={<Users />} />
          {routes.map(({ path }) => (
            <Route key={path} path={path} element={
              path === 'users' ? <Users /> :
              path === 'products' ? <Products /> :
              path === 'requests' ? <Requests /> : null
            } />
          ))}
        </Routes>
      </Box>
    </Box>
  );
}
