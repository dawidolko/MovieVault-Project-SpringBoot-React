type DashboardAlertProps = {
  heading: string;
  description: string;
};

const DashboardAlert: React.FC<DashboardAlertProps> = ({ heading, description }) => {
  return (
    <div className="page-wrapper">
      <div className="page-body">
        <div className="container-xl">
          <div className={`alert alert-danger`} role="alert">
            <div className="alert-icon">
              <i className="ti ti-alert-circle fs-1"></i>
            </div>
            <div>
              <h4 className="alert-heading">{heading}</h4>
              <div className="alert-description">{description}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardAlert;
