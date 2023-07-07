import React, { useState, useEffect } from "react";
import Head from "next/head";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useRouter } from "next/router";
import { Tooltip } from "@material-tailwind/react";
var People = [];
var Teams = [];

export default function Home() {
  let circleCommonClasses = "h-2.5 w-2.5 bg-blue-800   rounded-full";
  const [loading, setLoading] = useState(false);
  const [displayResults, setDisplayResults] = useState(false);
  const [fetchError, setFetchError] = useState(false);
  const [fetchSuccess, setFetchSuccess] = useState(false);
  const [AddisDisabled, setAddIsDisabled] = useState(false);
  const [CompleteisDisabled, setCompleteIsDisabled] = useState(true);
  const [SubmitisDisabled, setSubmitIsDisabled] = useState(true);
  const [Roles, setRoles] = useState(["\nTEAM PROFILE 1:"]);
  const [teamNumber, setTeamNumber] = useState(2);
  const [JSON, setJSON] = useState([
    {
      Teams: [
        {
          Actual: [
            {
              Person: {
                name: "",
                selected: "",
                roles: "",
                skills: "",
                availability: "",
                personality: "",
                SimilarityScore: "",
              },
            },
          ],
          Ideal: [
            {
              Person: {
                name: "",
                selected: "",
                roles: "",
                skills: "",
                availability: "",
                personality: "",
                SimilarityScore: "",
              },
            },
          ],
          Best: [
            {
              Person: {
                name: "",
                selected: "",
                roles: "",
                skills: "",
                availability: "",
                personality: "",
                SimilarityScore: "",
              },
            },
          ],
        },
      ],
    },
  ]);
  //methods to disable or enable buttons
  const AddDisable = () => {
    if (AddisDisabled == false) {
      setAddIsDisabled(true);
    }
  };
  const CompleteDisable = () => {
    if (CompleteisDisabled == false) {
      setCompleteIsDisabled(true);
    }
  };
  const CompleteEnable = () => {
    if (CompleteisDisabled == true) {
      setCompleteIsDisabled(false);
    }
  };
  const SubmitDisable = () => {
    if (SubmitisDisabled == true) {
      setSubmitIsDisabled(false);
    }
  };
  function sendJSON(JSON) {
    setLoading(true);
    const apiUrl = "http://localhost:8080/BJSS/POST";
    var response = fetch(apiUrl, {
      method: "POST",
      body: JSON,
    })
      .then((response) => {
        if (!response.ok) {
          return Promise.reject(response);
        }
        return response.json();
      })
      .then((data) => {
        setFetchSuccess(true);
        console.log("Success");
        console.log(data);
        setOuputJSON(data);
        setLoading(false);
        setDisplayResults(true);
      })
      .catch((error) => {
        if (typeof error.json === "function") {
          error
            .json()
            .then((jsonError) => {
              setLoading(false);
              setFetchError(true);
              console.log("Json error from API");
              console.log(jsonError);
            })
            .catch((genericError) => {
              setLoading(false);
              setFetchError(true);
              console.log("Generic error from API");
              console.log(error.statusText);
            });
        } else {
          setLoading(false);
          setFetchError(true);
          console.log("Unable to connect to the API, try again later");
          response = "Error occured";
        }
      });
  }
  const router = useRouter();

  const addPerson = (value) => {
    People.push(value);
  };
  const resetPeople = () => {
    People = [];
  };
  const addTeam = (value) => {
    Teams.push(value);
  };
  const addRole = (value) => {
    let arr = Roles;
    arr.push(value);
    setRoles(arr);
  };
  useEffect(() => {}, [Roles]);

  const setOuputJSON = (value) => {
    let arr = [value];
    setJSON(arr);
  };
  const addTeamNumber = () => {
    var number = teamNumber + 1;
    setTeamNumber(number);
  };
  const [btnClicked, setBtnClicked] = React.useState("");
  //Formik Logic
  const formik = useFormik({
    initialValues: {
      Location: "Aberdeen",
      EthicalType: "None",
      Clearance: "None",
      StartDate: "",
      Temperament: [],

      Role: "Project Manager",
      Amount: 1,
      Skill1: "Communication",
      Skill2: "None",
      Skill3: "None",
      Skill4: "None",
    },
    //Validate form

    validationSchema: Yup.object({
      StartDate: Yup.date().required("Date is required"),
    }),

    //submit form
    onSubmit: (values) => {
      if (btnClicked == "NewPerson") {
        var Person = "";
        //creating skill string
        var Skills = values.Skill1;
        if (values.Skill2 == "None") {
          Skills = Skills + ",";
        } else {
          Skills = Skills + "," + values.Skill2;
        }
        if (values.Skill3 == "None") {
          Skills = Skills + "";
        } else {
          Skills = Skills + "," + values.Skill3;
        }
        if (values.Skill4 == "None") {
          Skills = Skills + "";
        } else {
          Skills = Skills + "," + values.Skill4;
        }
        var WorkType = "";
        if (values.EthicalType == "None") {
          WorkType = "";
        } else {
          WorkType = values.EthicalType;
        }
        var Clearance = "";
        if (values.Clearance == "None") {
          Clearance = "";
        } else {
          Clearance = values.Clearance;
        }
        var Temperament = "";
        if (values.Temperament == []) {
          Temperament = "No";
        } else {
          Temperament = "Yes";
        }
        Person =
          values.Role +
          "&" +
          values.Location +
          "&" +
          Skills +
          "&" +
          values.StartDate +
          "&" +
          WorkType +
          "&" +
          Clearance +
          "&" +
          Temperament;
        for (var i = 0; i < values.Amount; i++) {
          addPerson(Person);
          CompleteEnable();
          addRole(values.Role);
        }
        if (People.length > 0) {
          setSubmitIsDisabled(true);
        }
      }
      if (btnClicked == "NewTeam") {
        var JSON = '{"Team":[';
        for (var i = 0; i < People.length; i++) {
          JSON = JSON + '"' + People[i] + '",';
        }
        var editedJSON = JSON.slice(0, -1);
        editedJSON = editedJSON + "]";
        console.log(editedJSON);
        addTeam(editedJSON);
        resetPeople();
        addTeamNumber();
        addRole("\nTEAM PROFILE " + teamNumber + ":");
        CompleteDisable();
        SubmitDisable();
      }
      if (btnClicked == "Submit") {
        var JSON = '{"Profiles":[';
        for (var i = 0; i < Teams.length; i++) {
          JSON = JSON + Teams[i] + "},";
        }
        var editedJSON = JSON.slice(0, -1);
        editedJSON = editedJSON + "]}";
        console.log(editedJSON);
        CompleteDisable();
        AddDisable();
        SubmitDisable();
        sendJSON(editedJSON);
      }
    },
  });

  return (
    <div>
      <Head>
        <title>BJSS Team Builder</title>
        <meta name="description" content="Generated by create next app" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className=" gap-y-20 flex-col flex items-center justify-center">
        <form
          onSubmit={formik.handleSubmit}
          className="bg-gray-200 flex rounded-lg w-3/4 font-latoRegular "
        >
          <div className="flex-1 text-gray-700 p-20">
            <h1 className="text-2xl pb-2 font-latoBold">
              Building the right Team
            </h1>
            <p className=" text-gray-900 pb-1">
              Create a team profile by first selecting the project profile
              information. To add people to the team profile select the role
              paramaters then add to team. You can create another team profile
              after clicking complete team. Submit when complete.
            </p>
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Select the Project team information"
              data-te-toggle="tooltip"
            >
              <h1 className="text-2xl pb-2 font-latoBold">Project Profile:</h1>
            </Tooltip>
            <div className="mt-6">
              {/* input field for Location */}
              <div className="pb-4">
                <label
                  className="block font-latoBold text-sm pb-2"
                  htmlFor="Location"
                >
                  Location
                </label>
                <select
                  name="Location"
                  value={formik.values.Location}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>Aberdeen</option>
                  <option>Birmingham</option>
                  <option>Bristol</option>
                  <option>Cardiff</option>
                  <option>Edinburgh</option>
                  <option>Exeter</option>
                  <option>Glasgow</option>
                  <option>Leeds</option>
                  <option>Lincoln</option>
                  <option>Liverpool</option>
                  <option>London</option>
                  <option>Manchester</option>
                  <option>Miltion Keynes</option>
                  <option>Newcastle </option>
                  <option>Nottingham </option>
                  <option>Reading </option>
                  <option>Sheffield </option>
                  <option>Swansea </option>
                  <option>York </option>
                </select>
              </div>
              {/* input field for EthicalType */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  Ethical-Type
                </label>
                <select
                  name="EthicalType"
                  value={formik.values.EthicalType}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>None</option>
                  <option>Climate</option>
                  <option>Military</option>
                  <option>Fashion</option>
                  <option>Monitoring</option>
                </select>
              </div>
              {/* input field for Clearance */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  Clearance
                </label>
                <select
                  name="Clearance"
                  value={formik.values.Clearance}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4
                   focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>None</option>
                  <option>Accreditation Check</option>
                  <option>Counter Terrorist Check</option>
                  <option>Security Check</option>
                  <option>Enhanced Security Check</option>
                  <option>Developed Vetting</option>
                  <option>Enhanced Developed Vetting</option>
                </select>
              </div>
              {/* date picker */}
              <div className="pb-4">
                <label
                  className={`block font-latoBold text-sm pb-2 ${
                    formik.touched.StartDate && formik.errors.StartDate
                      ? "text-red-400"
                      : ""
                  }`}
                >
                  {formik.touched.StartDate && formik.errors.StartDate
                    ? formik.errors.StartDate
                    : "Start Date"}
                </label>
                <input
                  name="StartDate"
                  value={formik.values.StartDate}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md
                   w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                  type="date"
                  onBlur={formik.handleBlur}
                />
              </div>
              {/* Temperament */}
              <Tooltip
                className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
                placement="top"
                content="Tool will create teams using the 16 personality types from Myers & Briggs test."
                data-te-toggle="tooltip"
              >
                <div className="flex items-center gap-2">
                  <label className="block font-latoBold text-sm pb-2">
                    Consider Personality?
                  </label>
                  <input
                    type="checkbox"
                    value="checked"
                    name="Temperament"
                    onChange={formik.handleChange}
                    className="h-5 w-5 border-2 border-gray-500 p-2 rounded-md focus:border-purple-200"
                  />
                </div>
              </Tooltip>
            </div>
          </div>
          <div className="flex-1 text-gray-700 p-20">
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Select the information for the role profile"
              data-te-toggle="tooltip"
            >
              <h1 className="text-2xl pb-2 font-latoBold">Person Profile:</h1>
            </Tooltip>
            <div className="mt-6">
              {/* input field for Role */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">Role</label>
                <select
                  name="Role"
                  value={formik.values.Role}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>Project Manager</option>
                  <option>Project Sponsor</option>
                  <option>Business Analyst</option>
                  <option>Team leader</option>
                  <option>Software Engineer</option>
                  <option>Test Engineer</option>
                  <option>Platform Engineer</option>
                  <option>Data Engineer</option>
                  <option>Data architects</option>
                  <option>User Research</option>
                  <option>Consultant</option>
                </select>
              </div>
              {/* input field for Amount */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  Role Amount
                </label>
                <select
                  name="Amount"
                  value={formik.values.Amount}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>1</option>
                  <option>2</option>
                  <option>3</option>
                  <option>4</option>
                  <option>5</option>
                  <option>6</option>
                  <option>7</option>
                  <option>8</option>
                  <option>9</option>
                  <option>10</option>
                </select>
              </div>
              {/* input field for Skill 1 */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  First Skill
                </label>
                <select
                  name="Skill1"
                  value={formik.values.Skill1}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>Communication</option>
                  <option>Approachability</option>
                  <option>Empathy</option>
                  <option>Teamwork</option>
                  <option>Creativity</option>
                  <option>Problem Solving</option>
                  <option>Patience</option>
                  <option>Decision Making</option>
                  <option>Resilience</option>
                  <option>Cooperation</option>
                  <option>Time management</option>
                  <option>Organization</option>
                  <option>Python</option>
                  <option>C#</option>
                  <option>C++</option>
                  <option>Javascript</option>
                  <option>Java</option>
                  <option>SQL</option>
                  <option>PHP</option>
                  <option>NODE JS</option>
                  <option>Django</option>
                  <option> React JS</option>
                  <option>Spring Boot</option>
                  <option>Booststrap</option>
                  <option>Flask</option>
                  <option>Android SDK</option>
                  <option>Cloud</option>
                  <option>Mobile</option>
                  <option>Distributed Systems</option>
                </select>
              </div>
              {/* input field for Skill 2 */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  Second Skill
                </label>
                <select
                  name="Skill2"
                  value={formik.values.Skill2}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>None</option>
                  <option>Communication</option>
                  <option>Approachability</option>
                  <option>Empathy</option>
                  <option>Teamwork</option>
                  <option>Creativity</option>
                  <option>Problem Solving</option>
                  <option>Patience</option>
                  <option>Decision Making</option>
                  <option>Resilience</option>
                  <option>Cooperation</option>
                  <option>Time management</option>
                  <option>Organization</option>
                  <option>Python</option>
                  <option>C#</option>
                  <option>C++</option>
                  <option>Javascript</option>
                  <option>Java</option>
                  <option>SQL</option>
                  <option>PHP</option>
                  <option>NODE JS</option>
                  <option>Django</option>
                  <option> React JS</option>
                  <option>Spring Boot</option>
                  <option>Booststrap</option>
                  <option>Flask</option>
                  <option>Android SDK</option>
                  <option>Cloud</option>
                  <option>Mobile</option>
                  <option>Distributed Systems</option>
                </select>
              </div>
              {/* input field for Skill 3 */}
              <div className="pb-4">
                <label className="block font-latoBold text-sm pb-2">
                  Third Skill
                </label>
                <select
                  name="Skill3"
                  value={formik.values.Skill3}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>None</option>
                  <option>Communication</option>
                  <option>Approachability</option>
                  <option>Empathy</option>
                  <option>Teamwork</option>
                  <option>Creativity</option>
                  <option>Problem Solving</option>
                  <option>Patience</option>
                  <option>Decision Making</option>
                  <option>Resilience</option>
                  <option>Cooperation</option>
                  <option>Time management</option>
                  <option>Organization</option>
                  <option>Python</option>
                  <option>C#</option>
                  <option>C++</option>
                  <option>Javascript</option>
                  <option>Java</option>
                  <option>SQL</option>
                  <option>PHP</option>
                  <option>NODE JS</option>
                  <option>Django</option>
                  <option> React JS</option>
                  <option>Spring Boot</option>
                  <option>Booststrap</option>
                  <option>Flask</option>
                  <option>Android SDK</option>
                  <option>Cloud</option>
                  <option>Mobile</option>
                  <option>Distributed Systems</option>
                </select>
              </div>
              {/* input field for Skill 4 */}
              <div className="pb-4">
                <label
                  className="block font-latoBold text-sm pb-2"
                  value={formik.values.Skill4}
                >
                  Fourth Skill
                </label>
                <select
                  name="Skill4"
                  value={formik.values.Skill4}
                  onChange={formik.handleChange}
                  className="border-2 border-gray-500 p-2 rounded-md w-3/4 focus:border-rgb(68, 48, 184) focus:ring-rgb(68, 48, 184)"
                >
                  <option>None</option>
                  <option>Communication</option>
                  <option>Approachability</option>
                  <option>Empathy</option>
                  <option>Teamwork</option>
                  <option>Creativity</option>
                  <option>Problem Solving</option>
                  <option>Patience</option>
                  <option>Decision Making</option>
                  <option>Resilience</option>
                  <option>Cooperation</option>
                  <option>Time management</option>
                  <option>Organization</option>
                  <option>Python</option>
                  <option>C#</option>
                  <option>C++</option>
                  <option>Javascript</option>
                  <option>Java</option>
                  <option>SQL</option>
                  <option>PHP</option>
                  <option>NODE JS</option>
                  <option>Django</option>
                  <option> React JS</option>
                  <option>Spring Boot</option>
                  <option>Booststrap</option>
                  <option>Flask</option>
                  <option>Android SDK</option>
                  <option>Cloud</option>
                  <option>Mobile</option>
                  <option>Distributed Systems</option>
                </select>
              </div>
            </div>
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Click to add role profile to the current team"
              data-te-toggle="tooltip"
            >
              <button
                className="bg-purple-800 font-latoBold text-sm
                 text-white py-3 mt-6 rounded-lg w-1/2 "
                type="submit"
                disabled={AddisDisabled}
                onClick={(event) => {
                  setBtnClicked("NewPerson");
                }}
              >
                {" "}
                Add To Team{" "}
              </button>
            </Tooltip>
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Click to complete the team profile"
              data-te-toggle="tooltip"
            >
              <button
                className="bg-purple-800 font-latoBold text-sm text-white py-3 mt-6 rounded-lg w-1/2 "
                type="submit"
                disabled={CompleteisDisabled}
                onClick={(event) => {
                  setBtnClicked("NewTeam");
                }}
              >
                {" "}
                Complete Team{" "}
              </button>
            </Tooltip>
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Click when you have finished the team profiles to calculate the best teams"
              data-te-toggle="tooltip"
            >
              <button
                className="bg-purple-800 font-latoBold text-sm text-white py-3 mt-6 rounded-lg w-1/2 "
                type="submit"
                disabled={SubmitisDisabled}
                onClick={(event) => {
                  setBtnClicked("Submit");
                }}
              >
                {" "}
                Submit{" "}
              </button>
            </Tooltip>
            {loading && (
              <div className="flex pt-10">
                <div
                  className={`${circleCommonClasses} mr-1 animate-bounce`}
                ></div>
                <div
                  className={`${circleCommonClasses} mr-1 animate-bounce200`}
                ></div>
                <div
                  className={`${circleCommonClasses} animate-bounce400`}
                ></div>
              </div>
            )}
            {fetchError && (
              <div className="flex pt-10">
                <h1 className="text-2xl pb-2 font-latoBold text-red-500">
                  Error: Could not connect to the REST service
                </h1>
              </div>
            )}
            {fetchSuccess && (
              <div className="flex pt-10">
                <h1 className="text-2xl pb-2 font-latoBold text-green-500">
                  Success! Check the results below
                </h1>
              </div>
            )}
          </div>
          <div className="flex-1 text-gray-700 py-20">
            {/* Current roles*/}
            <Tooltip
              className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
              placement="top"
              content="Here are the roles you have currently added to your team profiles"
              data-te-toggle="tooltip"
            >
              <h1 className="text-2xl pb-2 font-latoBold">Current Roles:</h1>
            </Tooltip>
            <ul className="text-lg text-gray-900 pb-1">
              {Array.isArray(Roles)
                ? Roles.map((value, index) => <li key={index}> {value}</li>)
                : null}
            </ul>
          </div>
        </form>
        {displayResults && (
          <div className="bg-gray-200 flex-row rounded-lg w-3/4 font-latoRegular ">
            {JSON.map((Team, index) => {
              return (
                <div key={index}>
                  {Team.Teams.map(function (team) {
                    return (
                      <div className="flex items-center py-10 justify-evenly">
                        <div className="bg-gray-200 flex-col rounded-lg  font-latoRegular w-fit ">
                          <Tooltip
                            className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
                            placement="top"
                            content="Team excluding unavailable people"
                            data-te-toggle="tooltip"
                          >
                            <h1 className="text-2xl pb-2 font-latoBold">
                              Actual People:
                            </h1>
                          </Tooltip>
                          {team.Actual.map(function (role) {
                            return (
                              <>
                                <ul className="block font-latoBold text-sm py-1">
                                  <li> Name: {role.Person.name}</li>
                                  <li>
                                    {" "}
                                    Selected Role: {role.Person.selected}
                                  </li>
                                  <li> Roles: {role.Person.roles}</li>
                                  <li> Skills: {role.Person.skills}</li>
                                  <li>
                                    {" "}
                                    Available: {role.Person.availability}
                                  </li>
                                  <li>
                                    {" "}
                                    Personality Type: {role.Person.personality}
                                  </li>
                                  <li>
                                    {" "}
                                    Similarity Score:{" "}
                                    {role.Person.SimilarityScore}
                                  </li>
                                </ul>
                                <hr class="h-px w-1/2 rounded mx-auto bg-gray-200 border-0 dark:bg-gray-400"></hr>
                              </>
                            );
                          })}
                        </div>
                        <div className="bg-gray-200 flex-col rounded-lg  font-latoRegular ">
                          <Tooltip
                            className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
                            placement="top"
                            content="Team including unavailable people"
                            data-te-toggle="tooltip"
                          >
                            <h1 className="text-2xl pb-2 font-latoBold">
                              Ideal People:
                            </h1>
                          </Tooltip>
                          {team.Ideal.map(function (role) {
                            return (
                              <>
                                <ul className="block font-latoBold text-sm py-1">
                                  <li> Name: {role.Person.name}</li>
                                  <li>
                                    {" "}
                                    Selected Role: {role.Person.selected}
                                  </li>
                                  <li> Roles: {role.Person.roles}</li>
                                  <li> Skills: {role.Person.skills}</li>
                                  <li>
                                    {" "}
                                    Available: {role.Person.availability}
                                  </li>
                                  <li>
                                    {" "}
                                    Personality Type: {role.Person.personality}
                                  </li>
                                  <li>
                                    {" "}
                                    Similarity Score:{" "}
                                    {role.Person.SimilarityScore}
                                  </li>
                                </ul>
                                <hr class="h-px w-1/2 rounded mx-auto bg-gray-200 border-0 dark:bg-gray-400"></hr>
                              </>
                            );
                          })}
                        </div>
                        <div className="bg-gray-200 flex-col rounded-lg  font-latoRegular ">
                          <Tooltip
                            className="block bg-blue-800 font-latoBold text-sm text-white pb-2 "
                            placement="top"
                            content="Best team if the start date can be changed ~1 month"
                            data-te-toggle="tooltip"
                          >
                            <h1 className="text-2xl pb-2 font-latoBold">
                              Preferred People:
                            </h1>
                          </Tooltip>
                          {team.Best.map(function (role) {
                            return (
                              <>
                                <ul className="block font-latoBold text-sm py-1">
                                  <li> Name: {role.Person.name}</li>
                                  <li>
                                    {" "}
                                    Selected Role: {role.Person.selected}
                                  </li>
                                  <li> Roles: {role.Person.roles}</li>
                                  <li> Skills: {role.Person.skills}</li>
                                  <li>
                                    {" "}
                                    Available: {role.Person.availability}
                                  </li>
                                  <li>
                                    {" "}
                                    Personality Type: {role.Person.personality}
                                  </li>
                                  <li>
                                    {" "}
                                    Similarity Score:{" "}
                                    {role.Person.SimilarityScore}
                                  </li>
                                </ul>
                                <hr class="h-px w-1/2 rounded mx-auto bg-gray-200 border-0 dark:bg-gray-400"></hr>
                              </>
                            );
                          })}
                        </div>
                      </div>
                    );
                  })}
                </div>
              );
            })}
          </div>
        )}
      </main>
    </div>
  );
}
