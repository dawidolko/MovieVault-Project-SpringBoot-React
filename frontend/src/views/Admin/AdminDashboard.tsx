import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { Link } from "react-router";
import ReactApexChart from "react-apexcharts";

const AdminDashboard = () => {
  const [overview, setOverview] = useState<any>({});
  const [genreDist, setGenreDist] = useState<any>({});
  const [reviewsMonth, setReviewsMonth] = useState<any>({});
  const [criticVsUser, setCriticVsUser] = useState<any>({});
  const [topReviewers, setTopReviewers] = useState<any[]>([]);

  useEffect(() => {
    api.get("/admin/analytics/overview").then((r) => setOverview(r.data)).catch(() => {});
    api.get("/admin/analytics/genre-distribution").then((r) => setGenreDist(r.data)).catch(() => {});
    api.get("/admin/analytics/reviews-per-month").then((r) => setReviewsMonth(r.data)).catch(() => {});
    api.get("/admin/analytics/critic-vs-user").then((r) => setCriticVsUser(r.data)).catch(() => {});
    api.get("/admin/analytics/most-active-reviewers").then((r) => setTopReviewers(r.data)).catch(() => {});
  }, []);

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none">
        <div className="container-xl">
          <div className="d-flex justify-content-between">
            <h2 className="page-title">Admin Dashboard</h2>
            <Link to="/admin/users" className="btn btn-primary">Manage Users</Link>
          </div>
        </div>
      </div>
      <div className="page-body">
        <div className="container-xl">
          <div className="row row-deck row-cards mb-4">
            <div className="col-sm-4">
              <div className="card"><div className="card-body">
                <div className="d-flex align-items-center">
                  <div className="subheader">Total Movies</div>
                  <div className="ms-auto"><span className="text-green d-inline-flex align-items-center lh-1"><i className="ti ti-movie fs-1" /></span></div>
                </div>
                <div className="h1 mb-0">{overview.totalMovies || 0}</div>
              </div></div>
            </div>
            <div className="col-sm-4">
              <div className="card"><div className="card-body">
                <div className="d-flex align-items-center">
                  <div className="subheader">Total Users</div>
                  <div className="ms-auto"><span className="text-blue d-inline-flex align-items-center lh-1"><i className="ti ti-users fs-1" /></span></div>
                </div>
                <div className="h1 mb-0">{overview.totalUsers || 0}</div>
              </div></div>
            </div>
            <div className="col-sm-4">
              <div className="card"><div className="card-body">
                <div className="d-flex align-items-center">
                  <div className="subheader">Total Reviews</div>
                  <div className="ms-auto"><span className="text-yellow d-inline-flex align-items-center lh-1"><i className="ti ti-star fs-1" /></span></div>
                </div>
                <div className="h1 mb-0">{overview.totalReviews || 0}</div>
              </div></div>
            </div>
          </div>

          <div className="row row-deck row-cards mb-4">
            <div className="col-lg-6">
              <div className="card">
                <div className="card-header"><h3 className="card-title">Genre Distribution</h3></div>
                <div className="card-body">
                  {Object.keys(genreDist).length > 0 && (
                    <ReactApexChart type="donut" height={300}
                      options={{ labels: Object.keys(genreDist), legend: { position: "bottom" } }}
                      series={Object.values(genreDist) as number[]} />
                  )}
                </div>
              </div>
            </div>
            <div className="col-lg-6">
              <div className="card">
                <div className="card-header"><h3 className="card-title">Reviews per Month</h3></div>
                <div className="card-body">
                  {Object.keys(reviewsMonth).length > 0 && (
                    <ReactApexChart type="bar" height={300}
                      options={{ xaxis: { categories: Object.keys(reviewsMonth) }, chart: { toolbar: { show: false } } }}
                      series={[{ name: "Reviews", data: Object.values(reviewsMonth) as number[] }]} />
                  )}
                </div>
              </div>
            </div>
          </div>

          <div className="row row-deck row-cards">
            <div className="col-lg-4">
              <div className="card">
                <div className="card-header"><h3 className="card-title">Critic vs User Ratings</h3></div>
                <div className="card-body">
                  <div className="d-flex justify-content-around text-center">
                    <div><div className="h1 text-red">{criticVsUser.critic?.toFixed(1) || "-"}</div><div className="text-secondary">Critic Avg</div></div>
                    <div><div className="h1 text-blue">{criticVsUser.user?.toFixed(1) || "-"}</div><div className="text-secondary">User Avg</div></div>
                  </div>
                </div>
              </div>
            </div>
            <div className="col-lg-8">
              <div className="card">
                <div className="card-header"><h3 className="card-title">Most Active Reviewers</h3></div>
                <div className="table-responsive">
                  <table className="table table-vcenter card-table">
                    <thead><tr><th>User</th><th>Reviews</th></tr></thead>
                    <tbody>
                      {topReviewers.map((r: any, i) => (
                        <tr key={i}><td>{r.firstName} {r.lastName}</td><td>{r.reviewCount}</td></tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
