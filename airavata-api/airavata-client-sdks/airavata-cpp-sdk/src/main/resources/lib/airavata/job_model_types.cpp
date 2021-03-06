/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "job_model_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>

namespace apache { namespace airavata { namespace model { namespace job {


JobModel::~JobModel() throw() {
}


void JobModel::__set_jobId(const std::string& val) {
  this->jobId = val;
}

void JobModel::__set_taskId(const std::string& val) {
  this->taskId = val;
}

void JobModel::__set_processId(const std::string& val) {
  this->processId = val;
}

void JobModel::__set_jobDescription(const std::string& val) {
  this->jobDescription = val;
}

void JobModel::__set_creationTime(const int64_t val) {
  this->creationTime = val;
__isset.creationTime = true;
}

void JobModel::__set_jobStatuses(const std::vector< ::apache::airavata::model::status::JobStatus> & val) {
  this->jobStatuses = val;
__isset.jobStatuses = true;
}

void JobModel::__set_computeResourceConsumed(const std::string& val) {
  this->computeResourceConsumed = val;
__isset.computeResourceConsumed = true;
}

void JobModel::__set_jobName(const std::string& val) {
  this->jobName = val;
__isset.jobName = true;
}

void JobModel::__set_workingDir(const std::string& val) {
  this->workingDir = val;
__isset.workingDir = true;
}

void JobModel::__set_stdOut(const std::string& val) {
  this->stdOut = val;
__isset.stdOut = true;
}

void JobModel::__set_stdErr(const std::string& val) {
  this->stdErr = val;
__isset.stdErr = true;
}

void JobModel::__set_exitCode(const int32_t val) {
  this->exitCode = val;
__isset.exitCode = true;
}

uint32_t JobModel::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_jobId = false;
  bool isset_taskId = false;
  bool isset_processId = false;
  bool isset_jobDescription = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->jobId);
          isset_jobId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->taskId);
          isset_taskId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 3:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->processId);
          isset_processId = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 4:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->jobDescription);
          isset_jobDescription = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 5:
        if (ftype == ::apache::thrift::protocol::T_I64) {
          xfer += iprot->readI64(this->creationTime);
          this->__isset.creationTime = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 6:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->jobStatuses.clear();
            uint32_t _size0;
            ::apache::thrift::protocol::TType _etype3;
            xfer += iprot->readListBegin(_etype3, _size0);
            this->jobStatuses.resize(_size0);
            uint32_t _i4;
            for (_i4 = 0; _i4 < _size0; ++_i4)
            {
              xfer += this->jobStatuses[_i4].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          this->__isset.jobStatuses = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 7:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->computeResourceConsumed);
          this->__isset.computeResourceConsumed = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 8:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->jobName);
          this->__isset.jobName = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 9:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->workingDir);
          this->__isset.workingDir = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 10:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->stdOut);
          this->__isset.stdOut = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->stdErr);
          this->__isset.stdErr = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          xfer += iprot->readI32(this->exitCode);
          this->__isset.exitCode = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_jobId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_taskId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_processId)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_jobDescription)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t JobModel::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("JobModel");

  xfer += oprot->writeFieldBegin("jobId", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->jobId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("taskId", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->taskId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("processId", ::apache::thrift::protocol::T_STRING, 3);
  xfer += oprot->writeString(this->processId);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("jobDescription", ::apache::thrift::protocol::T_STRING, 4);
  xfer += oprot->writeString(this->jobDescription);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.creationTime) {
    xfer += oprot->writeFieldBegin("creationTime", ::apache::thrift::protocol::T_I64, 5);
    xfer += oprot->writeI64(this->creationTime);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.jobStatuses) {
    xfer += oprot->writeFieldBegin("jobStatuses", ::apache::thrift::protocol::T_LIST, 6);
    {
      xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->jobStatuses.size()));
      std::vector< ::apache::airavata::model::status::JobStatus> ::const_iterator _iter5;
      for (_iter5 = this->jobStatuses.begin(); _iter5 != this->jobStatuses.end(); ++_iter5)
      {
        xfer += (*_iter5).write(oprot);
      }
      xfer += oprot->writeListEnd();
    }
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.computeResourceConsumed) {
    xfer += oprot->writeFieldBegin("computeResourceConsumed", ::apache::thrift::protocol::T_STRING, 7);
    xfer += oprot->writeString(this->computeResourceConsumed);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.jobName) {
    xfer += oprot->writeFieldBegin("jobName", ::apache::thrift::protocol::T_STRING, 8);
    xfer += oprot->writeString(this->jobName);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.workingDir) {
    xfer += oprot->writeFieldBegin("workingDir", ::apache::thrift::protocol::T_STRING, 9);
    xfer += oprot->writeString(this->workingDir);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.stdOut) {
    xfer += oprot->writeFieldBegin("stdOut", ::apache::thrift::protocol::T_STRING, 10);
    xfer += oprot->writeString(this->stdOut);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.stdErr) {
    xfer += oprot->writeFieldBegin("stdErr", ::apache::thrift::protocol::T_STRING, 11);
    xfer += oprot->writeString(this->stdErr);
    xfer += oprot->writeFieldEnd();
  }
  if (this->__isset.exitCode) {
    xfer += oprot->writeFieldBegin("exitCode", ::apache::thrift::protocol::T_I32, 12);
    xfer += oprot->writeI32(this->exitCode);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(JobModel &a, JobModel &b) {
  using ::std::swap;
  swap(a.jobId, b.jobId);
  swap(a.taskId, b.taskId);
  swap(a.processId, b.processId);
  swap(a.jobDescription, b.jobDescription);
  swap(a.creationTime, b.creationTime);
  swap(a.jobStatuses, b.jobStatuses);
  swap(a.computeResourceConsumed, b.computeResourceConsumed);
  swap(a.jobName, b.jobName);
  swap(a.workingDir, b.workingDir);
  swap(a.stdOut, b.stdOut);
  swap(a.stdErr, b.stdErr);
  swap(a.exitCode, b.exitCode);
  swap(a.__isset, b.__isset);
}

JobModel::JobModel(const JobModel& other6) {
  jobId = other6.jobId;
  taskId = other6.taskId;
  processId = other6.processId;
  jobDescription = other6.jobDescription;
  creationTime = other6.creationTime;
  jobStatuses = other6.jobStatuses;
  computeResourceConsumed = other6.computeResourceConsumed;
  jobName = other6.jobName;
  workingDir = other6.workingDir;
  stdOut = other6.stdOut;
  stdErr = other6.stdErr;
  exitCode = other6.exitCode;
  __isset = other6.__isset;
}
JobModel& JobModel::operator=(const JobModel& other7) {
  jobId = other7.jobId;
  taskId = other7.taskId;
  processId = other7.processId;
  jobDescription = other7.jobDescription;
  creationTime = other7.creationTime;
  jobStatuses = other7.jobStatuses;
  computeResourceConsumed = other7.computeResourceConsumed;
  jobName = other7.jobName;
  workingDir = other7.workingDir;
  stdOut = other7.stdOut;
  stdErr = other7.stdErr;
  exitCode = other7.exitCode;
  __isset = other7.__isset;
  return *this;
}
void JobModel::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "JobModel(";
  out << "jobId=" << to_string(jobId);
  out << ", " << "taskId=" << to_string(taskId);
  out << ", " << "processId=" << to_string(processId);
  out << ", " << "jobDescription=" << to_string(jobDescription);
  out << ", " << "creationTime="; (__isset.creationTime ? (out << to_string(creationTime)) : (out << "<null>"));
  out << ", " << "jobStatuses="; (__isset.jobStatuses ? (out << to_string(jobStatuses)) : (out << "<null>"));
  out << ", " << "computeResourceConsumed="; (__isset.computeResourceConsumed ? (out << to_string(computeResourceConsumed)) : (out << "<null>"));
  out << ", " << "jobName="; (__isset.jobName ? (out << to_string(jobName)) : (out << "<null>"));
  out << ", " << "workingDir="; (__isset.workingDir ? (out << to_string(workingDir)) : (out << "<null>"));
  out << ", " << "stdOut="; (__isset.stdOut ? (out << to_string(stdOut)) : (out << "<null>"));
  out << ", " << "stdErr="; (__isset.stdErr ? (out << to_string(stdErr)) : (out << "<null>"));
  out << ", " << "exitCode="; (__isset.exitCode ? (out << to_string(exitCode)) : (out << "<null>"));
  out << ")";
}

}}}} // namespace
