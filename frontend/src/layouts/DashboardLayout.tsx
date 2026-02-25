import React from "react";
import HeaderNav from "../components/HeaderNav/HeaderNav";
import Footer from "../components/Footer";
import { Outlet } from "react-router";
import { useState } from "react";

const DashboardLayout = () => {
  const [footerHeight, setFooterHeight] = useState(60);

  return (
    <div className="d-flex flex-column vh-100">
      <div className="sticky-top z-3">
        <HeaderNav />
      </div>

      <div className="flex-grow-1 overflow-auto" style={{ paddingBottom: `${footerHeight}px` }}>
        <Outlet />
      </div>

      <Footer onHeightChange={setFooterHeight} />
    </div>
  );
};

export default DashboardLayout;
