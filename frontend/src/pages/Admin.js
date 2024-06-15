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
import StorefrontIcon from '@mui/icons-material/Storefront';
import LocalMallIcon from '@mui/icons-material/LocalMall';
import HelpIcon from '@mui/icons-material/Help';
import CameraAltIcon from '@mui/icons-material/CameraAlt';
import CategoryIcon from '@mui/icons-material/Category';
import { Routes, Route, Link, useNavigate, useLocation } from 'react-router-dom';
import Users from './Users';
import Requests from './Requests';
import Buyers from './Buyers';
import Sellers from './Sellers';
import Photos from './Photos';
import Catalogs from './Catalogs';
import Categories from './Categories';
import { Button } from '@mui/material';

const routes = [
  {
    name: 'Sub-Admin',
    path: 'subadmin',
    accessCodes: [0],
  },
  {
    name: 'Sellers',
    path: 'sellers',
    accessCodes: [0, 1],
  },
  {
    name: 'Catalogs',
    path: 'catalog',
    accessCodes: [0, 1],
  },
  {
    name: 'Categories',
    path: 'categories',
    accessCodes: [0],
  },
  {
    name: 'Requests',
    path: 'requests',
    accessCodes: [0],
  },
  {
    name: 'Buyers',
    path: 'buyers',
    accessCodes: [0]
  },
  {
    name: 'Photos',
    path: 'photos',
    accessCodes: [0, 1],
  }
];

const drawerWidth = 240;


export default function Admin() {
  const navigate = useNavigate();
  const location = useLocation();
  const pathname = location.pathname?.split('/')[1]

  React.useEffect(() => {
    // CHANGE IT AT LAST
    if (localStorage.getItem("role") === null) {
      navigate("/login")
    }
  }, [navigate])

  const userAccessRole = localStorage.getItem('role') || 0;

  const handleNavigation = (path) => {
   navigate(path)
  };

  const handleLogout = async () => {
    await localStorage.removeItem('role');
    navigate('/login')
  }

  const renderRoutes = () => {
    return routes.map(({ name, path, accessCodes }) => {
      if (!accessCodes.includes(Number(userAccessRole))) return null;
      return (
        <ListItem key={name} disablePadding >
          <ListItemButton onClick={() => handleNavigation(path)} selected={pathname === path}>
            <ListItemIcon >
              {
                path === "subadmin" ? <AccountCircleIcon />
                  : path === "catalog" ? <ProductionQuantityLimitsIcon />
                    : path === "requests" ? <HelpIcon />
                      : path === "buyers" ? <LocalMallIcon />
                        : path === "sellers" ? <StorefrontIcon /> :
                          path === "photos" ? <CameraAltIcon /> :
                            path === "categories" ? <CategoryIcon /> :
                              <></>
              }
            </ListItemIcon>
            <ListItemText primary={name} />
          </ListItemButton>
        </ListItem>
      );
    });
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
      >
        <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Typography variant="h6" noWrap component="div">
            Dashboard
          </Typography>
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
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
          {renderRoutes()}
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
              path === 'subadmin' ? <Users /> :
              path === 'catalog' ? <Catalogs /> :
              path === 'requests' ? <Requests /> : 
              path === 'buyers' ? <Buyers/> :
              path === 'sellers' ? <Sellers/> :
              path === 'photos'? <Photos/> : 
              path === 'categories' ? <Categories/> :
              null
            } />
          ))}
        </Routes>
      </Box>
    </Box>
  );
}
